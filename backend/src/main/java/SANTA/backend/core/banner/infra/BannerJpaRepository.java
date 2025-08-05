package SANTA.backend.core.banner.infra;

import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.user.domain.Interest;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class BannerJpaRepository implements BannerRepository {

    private final EntityManager em;

    @Override
    public List<BannerEntity> findByInterest(Interest interest) {
        return em.createQuery("select b from BannerEntity b where b.interest = :interest", BannerEntity.class)
                .setParameter("interest", interest)
                .getResultList();
    }

    @Override
    public void saveBanners(List<Banner> banners) {
        int batchSize = 100;
        List<BannerEntity> bannerEntities = banners.stream().map(BannerEntity::from).toList();
        for (int i = 0; i < bannerEntities.size(); i++) {
            em.persist(bannerEntities.get(i));
            if (i % batchSize == 0) {
                em.flush();
                em.clear();
            }
            log.info("배너 {} 저장됨",bannerEntities.get(i).getName());
        }
    }
}
