package SANTA.backend.core.mountain.api;

import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
import SANTA.backend.core.mountain.dto.*;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mountains")
@RequiredArgsConstructor
public class MountainApi {

    private final MountainService mountainService;

    @GetMapping("search")
    public ResponseEntity<MountainSearchResponse> searchMountain(@RequestParam("mountainName") String mountainName){
        MountainSearchResponse mountainSearchResponse = mountainService.searchMountains(mountainName.trim());
        return ResponseEntity.ok(mountainSearchResponse);
    }

    @GetMapping("/{mountainId}/details/{pageNo}")
    public ResponseEntity<MountainNearByResponse> mountainNearBy(@RequestParam("location") String location, @PathVariable("pageNo") Long pageNo){
        MountainNearByResponse mountainNearByResponse = mountainService.searchNearByPlacesByLocation(location,pageNo);
        return ResponseEntity.ok().body(mountainNearByResponse);
    }

    @PostMapping("/optimalRoute")
    public ResponseEntity<OptimalRouteResponse> searchOptimalRoute(@RequestBody OptimalRouteRequest request) {
        OptimalRouteResponse optimalRouteResponse = mountainService.searchOptimalRoute(request);
        return ResponseEntity.ok().body(optimalRouteResponse);
    }

}
