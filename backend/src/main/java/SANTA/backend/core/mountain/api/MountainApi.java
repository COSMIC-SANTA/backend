package SANTA.backend.core.mountain.api;

import SANTA.backend.core.mountain.application.MountainService;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import SANTA.backend.core.mountain.dto.MountainSearchResponse;
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
    public ResponseEntity<MountainNearByResponse> mountainNearBy(@PathVariable("mountainId") Long mountainId, @PathVariable("pageNo") Long pageNo){
        MountainNearByResponse mountainNearByResponse = mountainService.searchNearByPlacesById(mountainId,pageNo);
        return ResponseEntity.ok().body(mountainNearByResponse);
    }
}
