package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URL;

@Component
public class WebClientService {

    private final WebClient webClient;

    public WebClientService(@Qualifier("clientServiceBean") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> request(URI uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }

}
