package SANTA.backend.core.banner.api;

import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import SANTA.backend.global.common.ResponseHandler;
import SANTA.backend.global.utils.api.rabbitmq.RabbitMQRequester;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseHandler<BannerResponse>> getBanner(@RequestParam String interest){

        BannerResponse response;
        Interest userInterest = Interest.valueOf(interest);
        response = bannerService.getInterestingMountains(userInterest);

        return ResponseEntity.ok().body(ResponseHandler.success(response));
    }
}