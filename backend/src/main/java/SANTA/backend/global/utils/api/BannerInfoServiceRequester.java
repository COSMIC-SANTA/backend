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
public class BannerInfoServiceRequester extends URIGenerator {

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    public Mono<JsonNode> getBanners(String locationName) {
        String url = appProperties.getBannerMountain().getUrl();
        String key = appProperties.getBannerMountain().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, locationName);
        System.out.println("요청 uri"+uri);
        return webClientService.request(uri);
    }

    public Mono<JsonNode> getBannerImage(Long mountainCode) {
        String url = appProperties.getBannerMountain().getImageUrl();
        String key = appProperties.getBannerMountain().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, mountainCode);
        log.info("배너 이미지 요청 uri: {}", uri);
        return webClientService.request(uri);
    }
}
