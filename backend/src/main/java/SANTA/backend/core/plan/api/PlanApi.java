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
    public ResponseEntity<ResponseHandler<Long>> saveCourse(@RequestBody PlanRequest courseRequest, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long planId = planService.savePlan(userDetails.getUserId(), courseRequest.targetDate(), courseRequest.mountain(), courseRequest.restaurants(),
                courseRequest.stays(), courseRequest.cafes(), courseRequest.spots());
        return ResponseEntity.ok().body(ResponseHandler.success(planId));
    }

    @PostMapping("/complete")
    public ResponseEntity<ResponseHandler<Long>> completePlan(@RequestBody CompletePlanRequest completePlanRequest){
        Long updatedPlanId = planService.completePlan(completePlanRequest.planId(), completePlanRequest.distance());
        return ResponseEntity.ok(ResponseHandler.success(updatedPlanId));
    }
}
