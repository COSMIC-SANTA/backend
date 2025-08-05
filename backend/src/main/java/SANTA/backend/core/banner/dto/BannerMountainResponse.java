package SANTA.backend.core.banner.dto;

import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.mountain.domain.Mountain;

public record BannerMountainResponse(
        Long id,
        String name,
        String image_url
) {
    public static BannerMountainResponse from(BannerEntity banner){
        return new BannerMountainResponse(banner.getId(), banner.getName(), banner.getImageUrl());
    }
}
