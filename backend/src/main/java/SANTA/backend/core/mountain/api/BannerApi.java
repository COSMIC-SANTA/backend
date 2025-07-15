package SANTA.backend.core.mountain.api;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.mountain.application.BannerService;
import SANTA.backend.core.mountain.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class BannerApi {

    private final BannerService bannerService;

    @GetMapping("/banner")
    public ResponseEntity<BannerResponse> getBanner(@AuthenticationPrincipal CustomUserDetails customUserDetails){
        Interest interest = customUserDetails.getInterest();
        return bannerService.getInterestingMountains(interest);
    }

}