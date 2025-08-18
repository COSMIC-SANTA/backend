package SANTA.backend.core.banner.application;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.dto.BannerDescriptionDTO;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface BannerService {
    BannerResponse getInterestingMountains(Interest interest);

    BannerResponse getPopularMountains();

    Banner findByName(String name);

    void saveBanners(List<Banner> banners);

    void incrementViewCount(String mountainName);

    BannerDescriptionDTO getBannerDescription(String mountainName);
}

