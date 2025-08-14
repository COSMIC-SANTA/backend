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
public class BannerDescriptionServiceRequester extends URIGenerator{

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    public Mono<JsonNode> getBannerDescription(String mountainName) {
        String url = appProperties.getBannerDescription().getUrl();
        String key = appProperties.getBannerDescription().getKey();
        URI uri = bannerDescriptionURIGenerator(url, key, mountainName);
        log.info("uri={}",uri);

        Mono<JsonNode> jsonNodeMono = webClientService.requestBannerDescription(uri);
        log.info("{} 배너 상세 설명 = {}", mountainName, jsonNodeMono.block().asText());
        return jsonNodeMono;

    }

}
