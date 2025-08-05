package SANTA.backend.global.utils.api;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import io.micrometer.common.lang.Nullable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface APIRequester {
    public Mono<MountainNearByResponse> searchNearByPlacesByLocation(String placeName, Long pageNo);

    public List<Banner> getBannersWithImages(@Nullable String mountainName);
}
