package SANTA.backend.mountain.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.banner.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

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
            Mountain mountain1 = Mountain.builder()
                    .visitCount(10L)
                    .sequence(0L)
                    .name("흥미 있는 산")
                    .difficulty(Difficulty.EASY)
                    .location("충북 청주")
                    .imageUrl("http://image")
                    .interest(Interest.ACTIVITY)
                    .build();

            Mountain mountain2 = Mountain.builder()
                    .visitCount(10L)
                    .sequence(0L)
                    .name("높은 산")
                    .difficulty(Difficulty.HARD)
                    .location("충북 청주")
                    .imageUrl("http://image")
                    .interest(Interest.HIGH)
                    .build();

            Mountain mountain3 = Mountain.builder()
                    .visitCount(11L)
                    .sequence(0L)
                    .name("낮은 산")
                    .difficulty(Difficulty.MODERATE)
                    .location("충북 청주")
                    .imageUrl("http://image")
                    .interest(Interest.LOW)
                    .build();

            mountainService.saveMountain(mountain1);
            mountainService.saveMountain(mountain2);
            mountainService.saveMountain(mountain3);

            //when
            BannerResponse interestingMountains1 = bannerService.getInterestingMountains(interest);

            //then
            assertThat(interestingMountains1).extracting(BannerResponse::interest).isEqualTo(interest);


        }

        public static Stream<Arguments> provideInterests() {
            return Stream.of(
                    Arguments.of(Interest.LOW),
                    Arguments.of(Interest.ACTIVITY),
                    Arguments.of(Interest.HIGH)
            );
        }
    }
}
