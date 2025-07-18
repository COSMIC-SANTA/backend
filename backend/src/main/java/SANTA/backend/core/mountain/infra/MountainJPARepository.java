package SANTA.backend.core.mountain.infra;

import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.mountain.entity.MountainEntity;
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
        List<MountainEntity> mountains = em.createQuery("select m from MountainEntity m where m.interest =: interest", MountainEntity.class)
                .setParameter("interest", interest)
                .getResultList();
        return mountains.stream().map(Mountain::fromEntity).toList();
    }

    @Override
    public void saveMountain(Mountain mountain) {
        MountainEntity mountainEntity = MountainEntity.from(mountain);
        em.persist(mountainEntity);
    }

    @Override
    public void saveMountains(List<Mountain> mountains) {
        int batchSize = 100;
        List<MountainEntity> mountainEntities = mountains.stream().map(MountainEntity::from).toList();
        for(int i=0; i<mountainEntities.size(); i++){
            em.persist(mountainEntities.get(i));
            if(i%batchSize==0){
                em.flush();
                em.clear();
            }
        }
    }

    @Override
    public Long findAllCount(){
        return em.createQuery("select count(m) from MountainEntity m", Long.class).getSingleResult();
    }

    @Override
    public Mountain findById(Long mountainId) {
        MountainEntity mountainEntity = em.find(MountainEntity.class, mountainId);
        return Mountain.fromEntity(mountainEntity);
    }

    @Override
    public List<Mountain> findByName(String name) {
        List<MountainEntity> mountainEntities = em.createQuery("select m from MountainEntity m where m.name =: name", MountainEntity.class)
                .setParameter("name", name)
                .getResultList();
        return mountainEntities.stream().map(Mountain::fromEntity).toList();
    }

}
