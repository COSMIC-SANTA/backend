package SANTA.backend.global.utils.api;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface APIRequester {
    public Mono<MountainNearByResponse> searchNearByPlacesByLocation(String placeName, Long pageNo);

    List<Mountain> getMountains();

    List<Mountain> getMountains(String locationName);
}
