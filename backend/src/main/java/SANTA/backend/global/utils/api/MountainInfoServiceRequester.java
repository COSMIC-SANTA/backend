package SANTA.backend.global.utils.api;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MountainInfoServiceRequester extends URIGenerator {

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    public Mono<JsonNode> getMountains() {
        String url = appProperties.getForest().getUrl();
        String key = appProperties.getForest().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, null);
        System.out.println(uri);
        return webClientService.request(uri);
    }

    public Mono<JsonNode> getMountains(String locationName) {
        String url = appProperties.getForest().getUrl();
        String key = appProperties.getForest().getKey();
        URI uri = mountainINfoServiceURIGenerator(url, key, locationName);
        System.out.println(uri);
        return webClientService.request(uri);
    }
}
