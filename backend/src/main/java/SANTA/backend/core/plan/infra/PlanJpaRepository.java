package SANTA.backend.core.plan.infra;

import SANTA.backend.core.plan.domain.PlanRepository;
import SANTA.backend.core.plan.entity.PlanEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PlanJpaRepository implements PlanRepository {

    private final EntityManager em;

    @Override
    public Optional<PlanEntity> findById(Long planId) {
        PlanEntity plan = em.createQuery("select p from PlanEntity p join fetch p.course where p.id =:planId", PlanEntity.class)
                .setParameter("planId", planId)
                .getSingleResult();
        return Optional.of(plan);
    }
}
