package SANTA.backend.core.mountain.infra;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.user.domain.Interest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MountainJPARepository implements MountainRepository {

    private final EntityManager em;

    @Override
    public List<MountainEntity> findByInterest(Interest interest){
        return em.createQuery("select m from MountainEntity m where m.interest =: interest", MountainEntity.class)
                .setParameter("interest", interest)
                .getResultList();
    }

    @Override
    public void saveMountain(Mountain mountain) {
        MountainEntity mountainEntity = MountainEntity.from(mountain);
        em.persist(mountainEntity);
    }

    @Override
    public Long findAllCount(){
        return em.createQuery("select count(m) from MountainEntity m", Long.class).getSingleResult();
    }

    @Override
    public Optional<MountainEntity> findById(Long mountainId) {
        MountainEntity mountainEntity = em.find(MountainEntity.class, mountainId);
        return Optional.of(mountainEntity);
    }

    @Override
    public List<MountainEntity> findByName(String name) {
        return em.createQuery("select m from MountainEntity m where m.name =: name", MountainEntity.class)
                .setParameter("name", name)
                .getResultList();
    }

}
