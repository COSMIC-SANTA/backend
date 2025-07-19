package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;
import java.util.stream.Stream;

public record BannerResponse(
        Interest interest,
        List<BannerMountainResponse> mountains
) {

    public static BannerResponse from(Interest interest, List<Mountain> mountains){
        return new BannerResponse(interest,mountains.stream().map(BannerMountainResponse::from).toList());
    }
}
