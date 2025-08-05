package SANTA.backend.core.banner.application;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;

public interface BannerService {
    BannerResponse getInterestingMountains(Interest interest);

    void saveBanners(List<Banner> banners);
}
