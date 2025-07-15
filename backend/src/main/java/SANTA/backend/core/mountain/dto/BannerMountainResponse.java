package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.mountain.domain.Mountain;

public record BannerMountainResponse(
        Long id,
        String name,
        String image_url
) {
    public static BannerMountainResponse from(Mountain mountain){
        return new BannerMountainResponse(mountain.getId(), mountain.getName(), mountain.getImageUrl());
    }
}
