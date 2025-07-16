package SANTA.backend.mountain.api;

import SANTA.backend.context.ControllerTest;
import SANTA.backend.context.ServiceContext;
import SANTA.backend.global.utils.api.domain.TouristApiResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.*;

public class ApiRequesterTest extends ServiceContext {

    @Nested
    class 한국_관광_공사_TourAPI_호출_테스트 {

        @Test
        void 지역으로_호출할_수_있다() {
            //when
            String contentByAreaBasedList2 = apiRequester.getContentByAreaBasedList2().block();

            //then
            assertThat(contentByAreaBasedList2).isNotNull();
            assertThat(contentByAreaBasedList2).contains("\"response\"");
        }

        @Test
        void 지역_코드를_조회할_수_있다(){
            //when
            String contentByAreaCode2 = apiRequester.getContentByAreaCode2(17L,1L).block();

            //then
            assertThat(contentByAreaCode2).isNotNull();
        }
    }
}
