package SANTA.backend.global.utils.api;

import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import reactor.core.publisher.Mono;

public interface APIRequester {
    public Mono<MountainNearByResponse> searchNearByPlacesByLocation(String placeName, Long pageNo);
}
