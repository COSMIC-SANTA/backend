package SANTA.backend.core.banner.infra;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.user.domain.Interest;

import java.util.List;
import java.util.Optional;

public interface BannerRepository {
    List<BannerEntity> findByInterest(Interest interest);

    List<BannerEntity> findPopularMountains();

    void saveBanners(List<Banner> banners);

    Optional<Banner> findById(Long bannerId);
}
