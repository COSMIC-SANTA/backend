package SANTA.backend.core.banner.api;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.global.common.ResponseHandler;
import SANTA.backend.global.utils.api.rabbitmq.RabbitMQRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class BannerApi {

    private final BannerService bannerService;

    private final RabbitMQRequester rabbitMQRequester;

    @PostMapping("saveMountainsFromApi")
    public void saveBannerMountains(){
        rabbitMQRequester.updateBanner(null);
    }

    @GetMapping("/banner")
    public ResponseEntity<ResponseHandler<BannerResponse>> getBanner(Interest interest, @RequestParam(defaultValue = "best") String type){

        BannerResponse response;
        if ("interest".equals(type)){
            response = bannerService.getInterestingMountains(interest);
        } else {
            response = bannerService.getPopularMountains();
        }
        return ResponseEntity.ok().body(ResponseHandler.success(response));
    }

}