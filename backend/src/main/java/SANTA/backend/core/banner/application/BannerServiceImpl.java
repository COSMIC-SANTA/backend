package SANTA.backend.core.banner.application;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.banner.infra.BannerRepository;
import SANTA.backend.core.user.domain.Interest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional(readOnly = true)
    public BannerResponse getInterestingMountains(Interest interest) {
        List<BannerEntity> bannerMountains = bannerRepository.findByInterest(interest);
        return BannerResponse.from(interest, bannerMountains);
    }

    @Override
    @Transactional(readOnly = true)
    public BannerResponse getPopularMountains() {
        List<BannerEntity> popularMountains = bannerRepository.findPopularMountains();
        return BannerResponse.from(null, popularMountains);
    }

    @Override @Transactional
    public void saveBanners(List<Banner> banners) {
        log.info("저장할 배너 개수: {}", banners.size()); // 🔍 로그 찍어보기
        bannerRepository.saveBanners(banners);
    }

    @Override @Transactional(readOnly = true)
    public Optional<Banner> findById(Long bannerId) {
        return bannerRepository.findById(bannerId);
    }
}
