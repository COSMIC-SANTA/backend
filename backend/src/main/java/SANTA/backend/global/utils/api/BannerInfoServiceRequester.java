package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BannerInfoServiceRequester extends URIGenerator {

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    public Mono<JsonNode> getBanners(String locationName) {
        String url = appProperties.getBannerMountain().getUrl();
        String key = appProperties.getBannerMountain().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, locationName);
        return webClientService.request(uri);
    }

    public Mono<JsonNode> getBannerImage(Long mountainCode) {
        String url = appProperties.getBannerMountain().getImageUrl();
        String key = appProperties.getBannerMountain().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, mountainCode);
        return webClientService.request(uri);
    }
}
