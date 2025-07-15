package SANTA.backend.core.mountain.application;

import SANTA.backend.core.mountain.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.springframework.http.ResponseEntity;

public interface BannerService {
    BannerResponse getInterestingMountains(Interest interest);
}
