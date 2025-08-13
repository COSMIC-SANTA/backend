package SANTA.backend.core.plan.application;

import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.plan.domain.PlanRepository;
import SANTA.backend.core.plan.entity.PlanEntity;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    @Transactional
    public Long completePlan(Long planId, Long distance) {
        PlanEntity plan  = planRepository.findById(planId)
                .orElseThrow(()-> new CustomException(ErrorCode.MOUNTAIN_NOT_FOUND, "해당되는 플랜이 존재하지 않습니다."));
        CourseEntity course = plan.getCourse();
        course.updateDistance(distance);
        plan.completePlan();
        return plan.getId();
    }
}
