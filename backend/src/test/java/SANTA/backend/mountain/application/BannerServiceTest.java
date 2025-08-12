package SANTA.backend.mountain.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.banner.dto.Banner;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class BannerServiceTest extends ServiceContext {

    @Nested
    class 배너_조회_테스트 {

        @ParameterizedTest
        @MethodSource({"provideInterests"})
        @Transactional
        void 관심사에_맞는_배너를_조회할_수_있다(Interest interest) {
            //given
            Banner banner1 = Banner.builder()
                    .code(1L)
                    .name("흥미 있는 산")
                    .location("충북 청주")
                    .interest(Interest.ACTIVITY)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.EASY)
                    .viewCount(10L)
                    .build();

            Banner banner2 = Banner.builder()
                    .code(2L)
                    .name("높은 산")
                    .location("충북 청주")
                    .interest(Interest.HIGH)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.HARD)
                    .viewCount(10L)
                    .build();

            Banner banner3 = Banner.builder()
                    .code(3L)
                    .name("낮은 산")
                    .location("충북 청주")
                    .interest(Interest.LOW)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.MODERATE)
                    .viewCount(11L)
                    .build();

            List<Banner> banners = List.of(banner1, banner2, banner3);
            bannerService.saveBanners(banners);

            //when
            BannerResponse interestingMountains1 = bannerService.getInterestingMountains(interest);

            //then
            assertThat(interestingMountains1).extracting(BannerResponse::interest).isEqualTo(interest);


        }

        @Test
        @Transactional
        void 인기_산_배너를_방문자수_순으로_조회할_수_있다() {
            // given
            Banner banner1 = Banner.builder()
                    .code(1L)
                    .name("한라산")
                    .location("제주도")
                    .interest(Interest.HIGH)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.HARD)
                    .viewCount(200L)
                    .build();

            Banner banner2 = Banner.builder()
                    .code(2L)
                    .name("지리산")
                    .location("전라남도")
                    .interest(Interest.ACTIVITY)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.HARD)
                    .viewCount(150L)
                    .build();

            Banner banner3 = Banner.builder()
                    .code(3L)
                    .name("부모산")
                    .location("청주")
                    .interest(Interest.LOW)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.MODERATE)
                    .viewCount(300L)
                    .build();

            List<Banner> banners = List.of(banner1, banner2, banner3);
            bannerService.saveBanners(banners);

            // when
            BannerResponse popularMountains = bannerService.getPopularMountains();

            // then
            assertThat(popularMountains.interest()).isEqualTo(Interest.POPULAR);
            assertThat(popularMountains.mountains()).hasSize(3);
            assertThat(popularMountains.mountains().get(0).name()).isEqualTo("부모산");
            assertThat(popularMountains.mountains().get(0).viewCount()).isEqualTo(300L);
            assertThat(popularMountains.mountains().get(1).name()).isEqualTo("한라산");
            assertThat(popularMountains.mountains().get(1).viewCount()).isEqualTo(200L);
            assertThat(popularMountains.mountains().get(2).name()).isEqualTo("지리산");
            assertThat(popularMountains.mountains().get(2).viewCount()).isEqualTo(150L);
        }

        public static Stream<Arguments> provideInterests() {
            return Stream.of(
                    Arguments.of(Interest.LOW),
                    Arguments.of(Interest.ACTIVITY),
                    Arguments.of(Interest.HIGH)
            );
        }
    }

    @Nested
    class 배너_클릭_테스트 {

        @Test
        @Transactional
        void 배너_클릭_시_조회수가_올라간다() {
            // given
            Banner banner = Banner.builder()
                    .code(1L)
                    .name("한라산")
                    .location("제주도")
                    .interest(Interest.HIGH)
                    .imageUrl("http://image")
                    .difficulty(Difficulty.HARD)
                    .viewCount(200L)
                    .build();

            bannerService.saveBanners(List.of(banner));

            // when
            bannerService.incrementViewCount("한라산");

            // then
            Banner updatedBanner = bannerService.findByName("한라산");
            assertThat(updatedBanner.getViewCount()).isEqualTo(201L);
        }
    }
}
