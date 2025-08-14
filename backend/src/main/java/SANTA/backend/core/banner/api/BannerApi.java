package SANTA.backend.core.banner.api;

import SANTA.backend.core.banner.application.BannerService;
import SANTA.backend.core.banner.dto.BannerDescriptionDTO;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
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
    private final MountainService mountainService;

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

    @PostMapping("/banner/click")
    public ResponseEntity<ResponseHandler<BannerDescriptionDTO>> getBannerClick(@RequestParam String mountainName){

        bannerService.incrementViewCount(mountainName);

        BannerDescriptionDTO response = bannerService.getBannerDescription(mountainName);

        return ResponseEntity.ok().body(ResponseHandler.success(response));
    }
}