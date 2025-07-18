package SANTA.backend.core.mountain.application;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.cafe.domain.CafeRepository;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.dto.MountainListSearchResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.external.ForestApiItem;
import SANTA.backend.core.mountain.dto.external.ForestApiResponse;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.restaurant.domain.RestaurantRepository;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.spot.domain.SpotRepository;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.stay.domain.StayRepository;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.ExternalApiException;
import SANTA.backend.global.exception.type.DataNotFoundException;

import SANTA.backend.global.utils.api.APIRequester;
import SANTA.backend.global.utils.api.KoreanTourInfoServiceRequester;
import org.springframework.core.codec.DecodingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
public class MountainService {

    private final WebClient webClient;

    @Value("${forest.api.key}")
    private String forestApiKey;

    private final MountainRepository mountainRepository;
    private final RestaurantRepository restaurantRepository;
    private final StayRepository stayRepository;
    private final CafeRepository cafeRepository;
    private final SpotRepository spotRepository;
    private final APIRequester apiRequester;

    public MountainService(@Qualifier("forestApiClient") WebClient webClient, MountainRepository mountainRepository, RestaurantRepository restaurantRepository,
                           StayRepository stayRepository, CafeRepository cafeRepository, SpotRepository spotRepository, APIRequester apiRequester) {
        this.webClient = webClient;
        this.mountainRepository = mountainRepository;
        this.restaurantRepository = restaurantRepository;
        this.stayRepository = stayRepository;
        this.cafeRepository = cafeRepository;
        this.spotRepository = spotRepository;
        this.apiRequester = apiRequester;
    }

    public MountainListSearchResponse searchMountains(String keyword) {

        ForestApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("")
                        .queryParam("searchWrd", keyword)
                        .queryParam("ServiceKey", forestApiKey)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    return Mono.error(new ExternalApiException(ErrorCode.FOREST_API_CONNECTION_ERROR,
                            "HTTP" + clientResponse.statusCode()));
                })
                .bodyToMono(ForestApiResponse.class)
                .onErrorMap(DecodingException.class, ex -> {
                    log.info("JSON 파싱 오류 - 검색 결과 없음: {}", keyword);
                    return new DataNotFoundException(ErrorCode.MOUNTAIN_NOT_FOUND,
                            "'" + keyword + "'에 대한 산 정보를 찾을 수 없습니다.");
                })
                .timeout(Duration.ofSeconds(10))
                .doOnError(error -> log.error("API 호출 중 오류: {}", error.getMessage()))
                .block();

        List<ForestApiItem> items = response.response().body().items().item();
        log.info("검색 결과 {}개 발견", items.size());

        return MountainListSearchResponse.from(items);
    }

    @Transactional
    public void saveMountain(Mountain mountain) {
        mountainRepository.saveMountain(mountain);
    }

    @Transactional
    public List<Mountain> findByName(String name){
        return mountainRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public MountainNearByResponse searchNearByPlacesById(Long mountainId, Long pageNo) {
        String location = mountainRepository.findById(mountainId).getLocation();
        Mono<MountainNearByResponse> mountainNearByResponseMono = apiRequester.searchNearByPlacesByLocation(location,pageNo);
        return mountainNearByResponseMono.block();
    }

    @Transactional
    public void saveMountains(List<Mountain> mountains) {
        mountainRepository.saveMountains(mountains);
    }
}
