package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.TouristApiResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientService {

    private final WebClient webClient;
    private static final String JSON = "json";

    public TouristApiResponse request(String url, String operation, String key, String mobileOs, String mobileApp) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url+"/"+operation)
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", mobileOs)
                        .queryParam("MobileApp", mobileApp)
                        .queryParam("_type",JSON)
                        .build())
                .retrieve().bodyToMono(TouristApiResponse.class)
                .block();
    }

    public TouristApiResponse request(String url, String operation, String key, String mobileOs, String mobileApp, int contentTyeId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url+"/"+operation)
                        .queryParam("serviceKey", key)
                        .queryParam("MobileOS", mobileOs)
                        .queryParam("MobileApp", mobileApp)
                        .queryParam("contentTypeId",contentTyeId)
                        .queryParam("_type",JSON)
                        .build())
                .retrieve().bodyToMono(TouristApiResponse.class)
                .block();
    }



}
