package SANTA.backend.mountain.infra;

import SANTA.backend.context.RepositoryContext;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.BannerResponse;
import SANTA.backend.core.user.domain.Interest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MountainRepositoryTest extends RepositoryContext {

    @Nested
    class 조회용_테스트{
        @ParameterizedTest @MethodSource({"provideInterests"}) @Transactional
        void 관심사에_맞는_산을_조회한다(Interest interest){
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

            mountainRepository.saveMountain(mountain1);
            mountainRepository.saveMountain(mountain2);
            mountainRepository.saveMountain(mountain3);

            //when
            List<Mountain> interestingMountains = mountainRepository.findByInterest(interest);

            //then
            assertThat(interestingMountains).extracting(Mountain::getInterest).containsExactlyInAnyOrder(interest);
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
