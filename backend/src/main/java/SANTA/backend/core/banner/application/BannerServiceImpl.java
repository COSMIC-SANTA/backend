package SANTA.backend.core.banner.application;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.dto.BannerDescriptionDTO;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.banner.infra.BannerRepository;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import SANTA.backend.global.utils.api.APIRequester;
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
    private final APIRequester apiRequester;

    @Override
    @Transactional(readOnly = true)
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
        BannerEntity bannerEntity = bannerRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.BANNER_NOT_FOUND));
        return Banner.fromEntity(bannerEntity);
    }

    @Override
    @Transactional
    public void saveBanners(List<Banner> banners) {
        log.info("저장할 배너 개수: {}", banners.size()); // 🔍 로그 찍어보기
        bannerRepository.saveBanners(banners);
    }

    @Override
    @Transactional
    public void incrementViewCount(String mountainName) {
        BannerEntity banner = bannerRepository.findByName(mountainName)
                .orElseThrow(() -> new CustomException(ErrorCode.BANNER_NOT_FOUND));
        banner.incrementViewCount();
        log.info("배너 '{}' 조회수 1 증가", mountainName);
    }

    @Override
    @Transactional(readOnly = true)
    public BannerDescriptionDTO getBannerDescription(String mountainName) {
        try {
            return apiRequester.getBannerDescription(mountainName).block();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.BANNER_API_INVALID,"배너 설명을 읽어오는 외부 API 사용이 불안정합니다.");
        }
    }
}
