package SANTA.backend.global.utils.api;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiRequesterImpl implements APIRequester {

    private final KoreanTourInfoServiceRequester koreanTourInfoServiceRequester;
    private final MountainInfoServiceRequester mountainInfoServiceRequester;
    private static final Long numOfRows = 20L;

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
    public List<Mountain> getMountains() {
        Mono<JsonNode> mountains = mountainInfoServiceRequester.getMountains();
        return extractMountains(mountains).block();
    }

    @Override
    public List<Mountain> getMountains(String locationName) {
        Mono<JsonNode> mountains = mountainInfoServiceRequester.getMountains(locationName);
        return List.of();
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
                            Position position = new Position(mapX,mapY);

                            switch (typeId) {
                                case RESTAURANT -> list.add((T) Restaurant.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case STAY -> list.add((T) Stay.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case CULTURAL_FACILITY -> list.add((T) Cafe.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
                                case TOUR_PLACE -> list.add((T) Spot.builder().name(name).location(location).imageUrl(imageUrl).position(position).build());
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

    private Mono<List<Mountain>> extractMountains(Mono<JsonNode> json){
        return json.map(jsonNodes -> {
            List<Mountain> mountains = new ArrayList<>();
            if(jsonNodes.isArray()){
                for (JsonNode jsonNode : jsonNodes) {
                    String mountainName = jsonNode.path("mtnm").asText();
                    String location = jsonNode.path("areanm").asText().split(",")[0].trim();
                    mountains.add(Mountain.builder().name(mountainName).location(location).build());
                }
            }
            return mountains;
        });
    }

}
