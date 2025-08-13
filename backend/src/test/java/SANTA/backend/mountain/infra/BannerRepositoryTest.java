package SANTA.backend.mountain.infra;

import SANTA.backend.context.RepositoryContext;
import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.banner.entity.BannerEntity;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BannerRepositoryTest extends RepositoryContext {

    @Nested
    class 조회용_테스트 {

        @Test
        @Transactional
        void 인기_산을_방문자수_내림차순으로_상위_10개_조회한다() {
            // given
            for (int i = 1; i <= 15; i++) {
                Banner banner = Banner.builder()
                        .code((long) i)
                        .name("산" + i)
                        .location("위치" + i)
                        .interest(Interest.ACTIVITY)
                        .imageUrl("http://image" + i)
                        .difficulty(Difficulty.MODERATE)
                        .viewCount((long) i * 100)
                        .build();
                bannerRepository.saveBanners(List.of(banner));
            }

            //when
            List<BannerEntity> result = bannerRepository.findPopularMountains();

            //then
            assertThat(result).hasSize(10);
            assertThat(result.get(0).getName()).isEqualTo("산15");
            assertThat(result.get(0).getViewCount()).isEqualTo(1500L);
            assertThat(result.get(9).getName()).isEqualTo("산6");
            assertThat(result.get(9).getViewCount()).isEqualTo(600L);

            for (int i = 0; i < result.size() - 1; i++) {
                assertThat(result.get(i).getViewCount()).isGreaterThanOrEqualTo(result.get(i + 1).getViewCount());
            }
        }
    }

}
