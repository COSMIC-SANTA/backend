package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class KaKaoMountainServiceRequester extends URIGenerator{

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    public Mono<JsonNode> searchMountainByKeyword(String mountainName){
        String url = appProperties.getKakaoSearchKeyword().getUrl();
        URI uri = kakaoSearchMountainURIGenerator(url, mountainName);
        log.info("검색 URI: {}", uri);

        return webClientService.requestSearchByKeyword(uri)
                .doOnNext(json -> log.info("산 검색 결과 정보 = {}", json.toString()))
                .doOnError(error -> log.error("산 검색 중 오류 발생: {}", error.getMessage()));
    }}
