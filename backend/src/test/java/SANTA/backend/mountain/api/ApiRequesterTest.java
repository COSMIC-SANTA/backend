package SANTA.backend.mountain.api;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ApiRequesterTest extends ServiceContext {

    @Nested
    class 한국_관광_공사_TourAPI_호출_테스트 {

        @Test
        void 지역으로_호출할_수_있다() {
            //when
            Long numOfRows = 20L, pageNo = 1L, sigunguCode = 1L;
            JsonNode contentByAreaBasedList1 = koreanTourInfoServiceRequester.getContentByAreaBasedList2(numOfRows, pageNo, AreaCode.SEOUL, sigunguCode, Arrange.A, ContentTypeId.RESTAURANT).block();
            JsonNode contentByAreaBasedList2 = koreanTourInfoServiceRequester.getContentByAreaBasedList2(numOfRows, pageNo, AreaCode.SEOUL, sigunguCode, Arrange.A, ContentTypeId.STAY).block();
            JsonNode contentByAreaBasedList3 = koreanTourInfoServiceRequester.getContentByAreaBasedList2(numOfRows, pageNo, AreaCode.SEOUL, sigunguCode, Arrange.A, ContentTypeId.CULTURAL_FACILITY).block();
            JsonNode contentByAreaBasedList4 = koreanTourInfoServiceRequester.getContentByAreaBasedList2(numOfRows, pageNo, AreaCode.SEOUL, sigunguCode, Arrange.A, ContentTypeId.TOUR_PLACE).block();
            System.out.println(contentByAreaBasedList1);
            System.out.println(contentByAreaBasedList2);
            System.out.println(contentByAreaBasedList3);
            System.out.println(contentByAreaBasedList4);


            //then
            assertThat(contentByAreaBasedList1).isNotNull();
        }

        @Test
        void 지역_코드를_조회할_수_있다() {
            //when
            JsonNode block = koreanTourInfoServiceRequester.getContentByAreaCode2(17L, 1L).block();
            JsonNode block1 = koreanTourInfoServiceRequester.getContentByAreaCode2(10L, 1L, AreaCode.BUSAN).block();
            System.out.println(block1);

            //then
            assertThat(block).isNotNull();
        }
    }
}
