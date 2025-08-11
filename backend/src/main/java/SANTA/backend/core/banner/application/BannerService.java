package SANTA.backend.core.banner.application;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface BannerService {
    BannerResponse getInterestingMountains(Interest interest);

    BannerResponse getPopularMountains();

    void saveBanners(List<Banner> banners);

    Optional<Banner> findById(Long bannerId);
}
