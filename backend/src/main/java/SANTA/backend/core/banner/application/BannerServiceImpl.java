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

@Service
@RequiredArgsConstructor
@Slf4j
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    @Transactional
    public BannerResponse getInterestingMountains(Interest interest) {
        if (interest == Interest.POPULAR) {
            return getPopularMountains();
        }

        List<BannerEntity> bannerMountains = bannerRepository.findByInterest(interest);
        return BannerResponse.from(interest, bannerMountains);
    }

    @Override
    @Transactional(readOnly = true)
    public BannerResponse getPopularMountains() {
        List<BannerEntity> popularMountains = bannerRepository.findPopularMountains();
        return BannerResponse.from(Interest.POPULAR, popularMountains);
    }

    @Override
    @Transactional(readOnly = true)
    public Banner findByName(String name) {
        BannerEntity bannerEntity = bannerRepository.findByName(name);
        return Banner.fromEntity(bannerEntity);
    }

    @Override @Transactional
    public void saveBanners(List<Banner> banners) {
        log.info("저장할 배너 개수: {}", banners.size()); // 🔍 로그 찍어보기
        bannerRepository.saveBanners(banners);
    }
}
