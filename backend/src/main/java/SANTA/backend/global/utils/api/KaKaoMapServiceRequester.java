package SANTA.backend.global.utils.api;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.global.common.AppProperties;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
@RequiredArgsConstructor
public class KaKaoMapServiceRequester extends URIGenerator{

    private static final Logger log = LoggerFactory.getLogger(KaKaoMapServiceRequester.class);
    private final AppProperties appProperties;
    private final WebClientService webClientService;


    public Mono<JsonNode> searchRoute(Position position, @Nullable List<Cafe> cafes, @Nullable List<Restaurant> restaurants, @Nullable List<Stay> stays, @Nullable List<Spot> spots, BasePlace destination){
        String url = appProperties.getKakao().getUrl();
        String key = appProperties.getKakao().getKey();
        URI uri = kakaoSearchRouteURIGenerator(url, position, cafes, restaurants, stays, spots, destination);
        log.info("uri={}",uri);
        Mono<JsonNode> jsonNodeMono = webClientService.requestKaKaoMap(uri);
        log.info("카카오 맵 결과 ={}",jsonNodeMono.block().asText());
        return jsonNodeMono;
    }
}
