package SANTA.backend.core.plan.domain;

import SANTA.backend.core.plan.entity.PlanEntity;

import java.util.List;
import java.util.Optional;

public interface PlanRepository {
    Optional<PlanEntity> findById(Long planId);

    Long savePlan(PlanEntity courseEntity);

    List<PlanEntity> findPlans(Long userId, PlanState planState);

}
