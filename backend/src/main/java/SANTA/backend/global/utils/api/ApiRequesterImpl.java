package SANTA.backend.global.utils.api;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.dto.*;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.core.weather.dto.WeatherResponseDto;
import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApiRequesterImpl implements APIRequester {

    private final KoreanTourInfoServiceRequester koreanTourInfoServiceRequester;
    private final BannerInfoServiceRequester bannerInfoServiceRequester;
    private final WeatherServiceRequester weatherServiceRequester;
    private static final Long numOfRows = 20L;
    private final KaKaoMountainServiceRequester kakaoMountainServiceRequester;
    private final KakaoFacilityServiceRequester kakaoFacilityServiceRequester;

    @Override
    public Mono<MountainNearByResponse> searchNearByPlacesByLocation(String location, Long pageNo) {
        String[] locations = location.split(" ");
        AreaCode areaCode = AreaCode.selectAreaCode(locations[0]);

        return getSigunguCode(areaCode, locations[1])
                .flatMap(sigunguCode -> {
                    Mono<List<Restaurant>> restaurantsMono = extractPlacesMono(numOfRows, pageNo, ContentTypeId.RESTAURANT, areaCode, sigunguCode);
                    Mono<List<Stay>> staysMono = extractPlacesMono(numOfRows, pageNo, ContentTypeId.STAY, areaCode, sigunguCode);
                    Mono<List<Cafe>> cafesMono = extractPlacesMono(numOfRows, pageNo, ContentTypeId.CULTURAL_FACILITY, areaCode, sigunguCode);
                    Mono<List<Spot>> spotsMono = extractPlacesMono(numOfRows, pageNo, ContentTypeId.TOUR_PLACE, areaCode, sigunguCode);

                    return Mono.zip(restaurantsMono, staysMono, cafesMono, spotsMono)
                            .map(tuple -> MountainNearByResponse.fromDomain(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4()));
                });
    }

    @Override
    public List<Banner> getBannersWithImages(@Nullable String locationName) {
        Mono<List<Banner>> bannersWithImage = bannerInfoServiceRequester.getBanners(locationName)
                .flatMap(this::extractBanners) // Step 1: JsonNode → List<Banner>
                .flatMapMany(Flux::fromIterable)
                .flatMap(this::enrichWithImageUrl) // Step 2: 각 Banner → 이미지 API 호출 → Banner에 imageUrl 추가
                .collectList();
        return bannersWithImage.block(); // block은 마지막에 한 번만
    }

    @Override
    public WeatherResponseDto getWeather(Position position) {
        return weatherServiceRequester.getWeather(position).block();
    }

    public Mono<MountainSearchResponse> searchMountains(String mountainName) {
        return kakaoMountainServiceRequester.searchMountainByKeyword(mountainName).map(this::extractMountainResponse);
    }

    @Override
    public Mono<MountainFacilityResponse> searchFacility(MountainFacilityRequest request) {
        return kakaoFacilityServiceRequester.searchFacilities(
                request.mapX(),
                request.mapY()
        ).map(this::extractFacilityResponse);
    }

    private <T extends BasePlace> Mono<List<T>> extractPlacesMono(Long numOfRows, Long pageNo, ContentTypeId typeId, AreaCode areaCode, Long sigunguCode) {
        return koreanTourInfoServiceRequester
                .getContentByAreaBasedList2(numOfRows, pageNo, areaCode, sigunguCode, Arrange.A, typeId)
                .map(jsonNode -> {
                    List<T> list = new ArrayList<>();
                    if (jsonNode.isArray()) {
                        for (JsonNode node : jsonNode) {
                            String name = node.path("title").asText();
                            String location = node.path("addr1").asText();
                            String imageUrl = node.path("firstimage").asText();
                            Double mapX = node.path("mapX").asDouble();
                            Double mapY = node.path("mapY").asDouble();
                            Position position = new Position(mapX, mapY);

                            switch (typeId) {
                                case RESTAURANT ->
                                        list.add((T) Restaurant.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case STAY ->
                                        list.add((T) Stay.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case CULTURAL_FACILITY ->
                                        list.add((T) Cafe.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case TOUR_PLACE ->
                                        list.add((T) Spot.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                            }
                        }
                    }
                    return list;
                });
    }

    private Mono<Long> getSigunguCode(AreaCode areaCode, String cityName) {
        return koreanTourInfoServiceRequester.getContentByAreaCode2(20L, 1L, areaCode)
                .flatMap(jsonNode -> {
                    if (jsonNode.isArray()) {
                        for (JsonNode item : jsonNode) {
                            if (item.path("name").asText().contains(cityName)) {
                                Long code = item.path("code").asLong();
                                return Mono.just(code);
                            }
                        }
                    }
                    return Mono.empty();
                });
    }

    private Mono<List<Banner>> extractBanners(JsonNode jsonNodes) {
        List<Banner> banners = new ArrayList<>();
        if (jsonNodes.isArray()) {
            for (JsonNode jsonNode : jsonNodes) {
                Long code = jsonNode.path("mntncd").asLong();
                String mountainName = jsonNode.path("mntnm").asText();
                String location = jsonNode.path("areanm").asText().split(",")[0].trim();
                long height = jsonNode.path("mntheight").asLong();
                Difficulty difficulty;
                if (height < 500)
                    difficulty = Difficulty.EASY;
                else if (height < 1000)
                    difficulty = Difficulty.MODERATE;
                else if (height < 1500)
                    difficulty = Difficulty.HARD;
                else
                    difficulty = Difficulty.EXTREME;

                banners.add(
                        Banner.builder()
                                .code(code)
                                .name(mountainName)
                                .location(location)
                                .interest(height > 1000L ? Interest.HIGH : Interest.LOW)
                                .difficulty(difficulty)
                                .build()
                );
            }
        }
        return Mono.just(banners);
    }


    private Mono<Banner> enrichWithImageUrl(Banner banner) {
        return bannerInfoServiceRequester.getBannerImage(banner.getCode())
                .map(jsonNode -> {
                    String imageUrl = jsonNode.path("image").asText(null); // 없는 경우 null 반환
                    banner.setImageUrl(imageUrl);
                    return banner;
                })
                .onErrorResume(e -> {
                    return Mono.just(banner); // 이미지 없이 그냥 반환
                });
    }

    private MountainSearchResponse extractMountainResponse(JsonNode mountains) {
        List<MountainDTO> mountainList = new ArrayList<>();
        if (mountains.isArray()) {
            for (JsonNode mountain : mountains) {
                mountainList.add(new MountainDTO(
                        mountain.path("place_name").asText(),
                        mountain.path("address_name").asText(),
                        mountain.path("x").asText(),
                        mountain.path("y").asText()
                ));
            }
        }
        return new MountainSearchResponse(mountainList);
    }

    private MountainFacilityResponse extractFacilityResponse(JsonNode jsonNode) {
        JsonNode hospitalsNode = jsonNode.path("hospitals");
        JsonNode pharmaciesNode = jsonNode.path("pharmacies");
        JsonNode toiletsNode = jsonNode.path("toilets");
        JsonNode waterNode = jsonNode.path("water");

        List<FacilityDTO> hospitals = parseFacilities(hospitalsNode);
        List<FacilityDTO> pharmacies = parseFacilities(pharmaciesNode);
        List<FacilityDTO> toilets = parseFacilities(toiletsNode);
        List<FacilityDTO> water = parseFacilities(waterNode);

        return new MountainFacilityResponse(
                sortByDistance(toilets), // toilet
                sortByDistance(water), // water
                sortByDistance(hospitals), // hospital
                sortByDistance(pharmacies) // pharmacy
        );
    }

    private List<FacilityDTO> parseFacilities(JsonNode jsonNode) {
        List<FacilityDTO> facilities = new ArrayList<>();

        if(jsonNode.isArray()) {
            for (JsonNode document : jsonNode) {
                String placeName = document.path("place_name").asText();
                String addressName = document.path("road_address_name").asText();
                String mapX = document.path("x").asText();
                String mapY = document.path("y").asText();
                String distance = document.path("distance").asText();

                facilities.add(new FacilityDTO(placeName, addressName, mapX, mapY, distance));
            }
        }

        return facilities;
    }

    private List<FacilityDTO> sortByDistance(List<FacilityDTO> facilities) {
        return facilities.stream()
                .sorted(Comparator.comparingInt(facility ->
                        facility.distance().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(facility.distance())))
                .toList();
    }
}
