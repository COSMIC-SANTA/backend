package SANTA.backend.core.spot.infra;

import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.spot.domain.SpotRepository;
import SANTA.backend.core.spot.entity.SpotEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class SpotJPARepository implements SpotRepository {

    private final EntityManager em;

    @Override
    public List<Spot> findByLocation(String location) {
        List<SpotEntity> spotEntities = em.createQuery("select s from SpotEntity s where s.location =: location", SpotEntity.class)
                .setParameter("location", location)
                .getResultList();
        return spotEntities.stream().map(Spot::fromEntity).toList();
    }
}
