package SANTA.backend.core.mountain.infra;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.user.domain.Interest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MountainJPARepository implements MountainRepository {

    private final EntityManager em;

    @Override
    public List<Mountain> findByInterest(Interest interest){
        return em.createQuery("select m from MountainEntity m where m.interest =: interest", Mountain.class)
                .setParameter("interest",interest)
                .getResultList();
    }

}
