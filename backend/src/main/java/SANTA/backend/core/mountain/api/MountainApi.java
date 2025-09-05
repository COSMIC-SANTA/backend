package SANTA.backend.core.mountain.api;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.dto.MountainFacilityRequest;
import SANTA.backend.core.mountain.dto.MountainFacilityResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.mountain.dto.OptimalRouteRequest;
import SANTA.backend.core.mountain.dto.OptimalRouteResponse;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mountains")
@RequiredArgsConstructor
@Slf4j
public class MountainApi {

    private final MountainService mountainService;

    @GetMapping("search")
    public ResponseEntity<MountainSearchResponse> searchMountain(@RequestParam("mountainName") String mountainName) {
        log.info("mountainName={}",mountainName);
        MountainSearchResponse mountainSearchResponse = mountainService.searchMountains(mountainName.trim());
        log.info("result={}",mountainSearchResponse);
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
        log.info("request.destination ={}", request.destination().getName());
        log.info("request.origin x={}, y={}", request.origin().getMapX(), request.origin().getMapY());
        log.info("request.mountain ={}", request.mountain().getName());
        for (Cafe cafe : request.cafes()) {
            log.info("request.cafe ={}", cafe.getName());
        }
        for (Restaurant restaurant : request.restaurants()) {
            log.info("request.restaurant ={}", restaurant.getName());
        }
        for (Stay stay : request.stays()) {
            log.info("request.stay ={}", stay);
        }
        for (Spot spot : request.spots()) {
            log.info("request.spot ={}", spot);
        }
        OptimalRouteResponse optimalRouteResponse = mountainService.searchOptimalRoute(request);
        return ResponseEntity.ok().body(optimalRouteResponse);
    }

}

