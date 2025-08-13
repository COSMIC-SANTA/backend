package SANTA.backend.core.plan.domain;

import SANTA.backend.core.plan.entity.PlanEntity;

import java.util.Optional;

public interface PlanRepository {
    Optional<PlanEntity> findById(Long planId);
}
