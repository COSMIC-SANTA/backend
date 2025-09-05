package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class WebClientService {

    private final WebClient webClient;
    private final WebClient kakaoWebClient;
    private final WebClient kakaoSearchByKeywordClient;
    private final WebClient bannerDescriptionClient;
    private final AppProperties appProperties;

    public WebClientService(@Qualifier("clientServiceBean") WebClient webClient, @Qualifier("kakaoRouteClient") WebClient kakaoWebClient, @Qualifier("kakaoSearchByKeywordClient") WebClient kakaoSearchByKeywordClient, @Qualifier("forestApiClient") WebClient bannerDescriptionClient,
                            AppProperties appProperties) {
        this.webClient = webClient;
        this.kakaoWebClient = kakaoWebClient;
        this.kakaoSearchByKeywordClient = kakaoSearchByKeywordClient;
        this.bannerDescriptionClient = bannerDescriptionClient;
        this.appProperties = appProperties;
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
                .header("Authorization", "KakaoAK " + appProperties.getKakao().getKey())
                .retrieve()
                .bodyToMono(JsonNode.class);

        return stringMono.map(json -> {
            try {
                return json.path("routes");
            } catch (Exception e) {
                throw new RuntimeException("JSON 파싱 오류", e);
            }
        });
    }

    public Mono<JsonNode> requestSearchByKeyword(URI uri){
        Mono<JsonNode> stringMono = kakaoSearchByKeywordClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(JsonNode.class);

        return stringMono.map(json -> {
            try {
                return json.path("documents");
            } catch (Exception e) {
                throw new RuntimeException("JSON 파싱 오류", e);
            }
        });
    }

    public Mono<JsonNode> requestBannerDescription(URI uri){
        Mono<JsonNode> stringMono = bannerDescriptionClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(JsonNode.class);

        return stringMono.map(json -> {
            try {
                return json.path("response").path("body").path("items").path("item");
            } catch (Exception e) {
                throw new RuntimeException("JSON 파싱 오류", e);
            }
        });
    }
}

