package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoFacilityServiceRequester extends URIGenerator{

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    private static final String HOSPITAL_KEYWORD = "병원";
    private static final String PHARMACY_KEYWORD = "약국";
    private static final String TOILET_KEYWORD = "화장실";
    private static final String WATER_KEYWORD = "음수대";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public Mono<JsonNode> searchFacilities(String mapX, String mapY) {
        Mono<JsonNode> hospitalsMono = searchHospitals(mapX, mapY);
        Mono<JsonNode> pharmaciesMono = searchPharmacies(mapX, mapY);
        Mono<JsonNode> toiletsMono = searchToilets(mapX, mapY);
        Mono<JsonNode> waterMono = searchWater(mapX, mapY);

        return Mono.zip(hospitalsMono, pharmaciesMono, toiletsMono, waterMono)
                .map(tuple -> {
                    ObjectNode result = MAPPER.createObjectNode();
                    result.set("hospitals", tuple.getT1());
                    result.set("pharmacies", tuple.getT2());
                    result.set("toilets", tuple.getT3());
                    result.set("water", tuple.getT4());
                    return result;
                });
    }

    public Mono<JsonNode> searchHospitals(String mapX, String mapY) {
        return searchFacilitiesByKeyword(mapX, mapY, HOSPITAL_KEYWORD);
    }

    public Mono<JsonNode> searchPharmacies(String mapX, String mapY) {
        return searchFacilitiesByKeyword(mapX, mapY, PHARMACY_KEYWORD);
    }

    public Mono<JsonNode> searchToilets(String mapX, String mapY) {
        return searchFacilitiesByKeyword(mapX, mapY, TOILET_KEYWORD);
    }

    public Mono<JsonNode> searchWater(String mapX, String mapY) {
        return searchFacilitiesByKeyword(mapX, mapY, WATER_KEYWORD);
    }

    public Mono<JsonNode> searchFacilitiesByKeyword(String mapX, String mapY, String keyword){
        String url = appProperties.getKakaoSearchKeyword().getUrl();
        URI uri = kakaoSearchFacilityURIGenerator(url, mapX, mapY, keyword);
        log.info("검색 URI: {}", uri);

        return webClientService.requestSearchByKeyword(uri)
                .doOnNext(json -> log.info("({},{}) 근처 편의 시설 정보 = {}", mapX, mapY, json.toString()))
                .doOnError(error -> log.error("({},{}) 근처 편의 시설 검색 중 오류 발생: {}", mapX, mapY, error.getMessage()));
    }
}
