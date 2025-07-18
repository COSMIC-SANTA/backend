package SANTA.backend.core.cafe.infra;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.cafe.domain.CafeRepository;
import SANTA.backend.core.cafe.entity.CafeEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CafeJPARepository implements CafeRepository {

    private final EntityManager em;

    @Override
    public List<Cafe> findByLocation(String location) {
        List<CafeEntity> cafeEntities = em.createQuery("select c from CafeEntity c where c.location =: location", CafeEntity.class)
                .setParameter("location", location)
                .getResultList();

        return cafeEntities.stream().map(Cafe::fromEntity).toList();
    }
}
