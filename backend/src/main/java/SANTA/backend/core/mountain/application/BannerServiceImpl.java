package SANTA.backend.core.mountain.application;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

    private final MountainRepository mountainRepository;

    @Override
    public BannerResponse getInterestingMountains(Interest interest) {
        List<Mountain> bannerMountains = mountainRepository.findByInterest(interest);
        return BannerResponse.from(interest,bannerMountains);
    }
}
