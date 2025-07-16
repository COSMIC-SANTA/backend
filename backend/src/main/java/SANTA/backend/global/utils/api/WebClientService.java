package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.TouristApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class WebClientService {

    private final WebClient webClient;
    private static final String JSON = "json";

    public WebClientService(@Qualifier("clientServiceBean") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> request(String url, String operation, String key, String mobileOs, String mobileApp) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(url)                   // http://apis.data.go.kr/...
                .pathSegment(operation)             // /areaBasedList2
                .queryParam("serviceKey", key)
                .queryParam("MobileOS", mobileOs)
                .queryParam("MobileApp", mobileApp)
                .queryParam("_type", JSON)
                .build(true)
                .toUri();

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

    public TouristApiResponse request(String url, String operation, String key, String mobileOs, String mobileApp, int contentTyeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url + "/" + operation)
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", mobileOs)
                        .queryParam("MobileApp", mobileApp)
                        .queryParam("contentTypeId", contentTyeId)
                        .queryParam("_type", JSON)
                        .build())
                .retrieve().bodyToMono(TouristApiResponse.class)
                .block();
    }


}
