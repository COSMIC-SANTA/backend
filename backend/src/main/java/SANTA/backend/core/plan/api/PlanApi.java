package SANTA.backend.core.plan.api;

import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.plan.dto.PlanRequest;
import SANTA.backend.core.mountain.dto.CompletePlanRequest;
import SANTA.backend.core.plan.application.PlanService;
import SANTA.backend.global.common.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/plan")
public class PlanApi {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<ResponseHandler<Long>> savePlan(@RequestBody PlanRequest planRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long planId = planService.savePlan(userDetails.getUserId(), planRequest.targetDate(), planRequest.mountain(), planRequest.restaurantDTO(),
                planRequest.stayDTO(), planRequest.cafeDTO(), planRequest.touristSpotDTO());
        return ResponseEntity.ok().body(ResponseHandler.success(planId));
    }

    @PostMapping("/complete")
    public ResponseEntity<ResponseHandler<Long>> completePlan(@RequestBody CompletePlanRequest completePlanRequest){
        Long updatedPlanId = planService.completePlan(completePlanRequest.planId(), completePlanRequest.distance());
        return ResponseEntity.ok(ResponseHandler.success(updatedPlanId));
    }
}
