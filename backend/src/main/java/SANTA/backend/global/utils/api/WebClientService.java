package SANTA.backend.global.utils.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class WebClientService {

    private final WebClient webClient;
    private final WebClient kakaoWebClient;

    public WebClientService(@Qualifier("clientServiceBean") WebClient webClient, @Qualifier("kakaoRouteClient") WebClient kakaoWebClient) {
        this.webClient = webClient;
        this.kakaoWebClient = kakaoWebClient;
    }

    public Mono<JsonNode> request(URI uri) {
        Mono<String> stringMono = webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono.map(json -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(json);

                return root.path("response").path("body").path("items").path("item");
            } catch (Exception e) {
                throw new RuntimeException("JSON 파싱 오류", e);
            }
        });
    }

    public Mono<JsonNode> requestKaKaoMap(URI uri){
        Mono<JsonNode> stringMono = kakaoWebClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class);

        stringMono.map(json -> {
            try {
                return json.path("routes");
            } catch (Exception e) {
                throw new RuntimeException("JSON 파싱 오류", e);
            }
        });
        return stringMono;
    }

}
