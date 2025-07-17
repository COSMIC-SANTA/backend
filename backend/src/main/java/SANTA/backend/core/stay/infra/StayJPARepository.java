package SANTA.backend.core.stay.infra;

import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.stay.domain.StayRepository;
import SANTA.backend.core.stay.entity.StayEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StayJPARepository implements StayRepository {

    private final EntityManager em;

    @Override
    public List<Stay> findByLocation(String location) {
        List<StayEntity> stayEntities = em.createQuery("select s from StayEntity s where s.location =: location", StayEntity.class)
                .setParameter("location", location)
                .getResultList();
        return stayEntities.stream().map(Stay::fromEntity).toList();
    }
}
