package SANTA.backend.core.mountain.api;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.dto.MountainFacilityRequest;
import SANTA.backend.core.mountain.dto.MountainFacilityResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.mountain.dto.OptimalRouteRequest;
import SANTA.backend.core.mountain.dto.OptimalRouteResponse;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mountains")
@RequiredArgsConstructor
public class MountainApi {

    private final MountainService mountainService;

    @GetMapping("search")
    public ResponseEntity<MountainSearchResponse> searchMountain(@RequestParam("mountainName") String mountainName) {
        MountainSearchResponse mountainSearchResponse = mountainService.searchMountains(mountainName.trim());
        return ResponseEntity.ok(mountainSearchResponse);
    }

    @GetMapping("/{location}/{pageNo}")
    public ResponseEntity<MountainNearByResponse> mountainNearBy(@PathVariable("location") String location, @PathVariable("pageNo") Long pageNo) {
        MountainNearByResponse mountainNearByResponse = mountainService.searchNearByPlacesByLocation(location, pageNo);
        return ResponseEntity.ok().body(mountainNearByResponse);
    }

    @PostMapping("/facilities")
    public ResponseEntity<MountainFacilityResponse> searchFacilities(@RequestBody MountainFacilityRequest mountainFacilityRequest){
        MountainFacilityResponse mountainFacilityResponse = mountainService.searchFacilities(mountainFacilityRequest);
        return ResponseEntity.ok().body(mountainFacilityResponse);
    }

    @PostMapping("/optimalRoute")
    public ResponseEntity<OptimalRouteResponse> searchOptimalRoute(@RequestBody OptimalRouteRequest request) {
        OptimalRouteResponse optimalRouteResponse = mountainService.searchOptimalRoute(request);
        return ResponseEntity.ok().body(optimalRouteResponse);
    }

//    @GetMapping("/complete")
//    public ResponseEntity<ResponseHandler<>> getCompletedMountains(@AuthenticationPrincipal CustomUserDetails userDetails){
//        userDetails.
//    }

}
