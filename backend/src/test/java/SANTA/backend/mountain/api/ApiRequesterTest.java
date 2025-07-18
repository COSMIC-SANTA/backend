package SANTA.backend.mountain.api;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

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

    @Nested
    class 산림청_명산등산로_API_호출_테스트 {

        @Test
        void 산_정보들을_조회할_수_있다() {
            // when
            JsonNode mountains = mountainInfoServiceRequester.getMountains().block();
            System.out.println("갯수: " + mountains.size()); // 배열 크기 출력

            // then
            assertThat(mountains.isArray()).isTrue();
            int i=0;
            for (JsonNode mountain : mountains) {
                String name = mountain.path("mntiname").asText();    // 산 이름
                String area = mountain.path("mntiadd").asText();     // 지역
                String height = mountain.path("mntihigh").asText();  // 높이

                System.out.println("산 이름: " + name);
                System.out.println("지역: " + area);
                System.out.println("높이: " + height);
                System.out.println("갯수: " +i +"번째");

                assertThat(name).isNotBlank();
                assertThat(area).isNotBlank();
                assertThat(height).isNotBlank();
                i++;
            }
        }


        @Test
        void 산_이름으로_정보들을_조회할_수_있다(){
            //when
            String locationName = "충청북도";
            JsonNode mountains = mountainInfoServiceRequester.getMountains(locationName).block();
            System.out.println(mountains.asText());

            //then
            assertThat(mountains.isArray()).isTrue();

            for (JsonNode mountain : mountains) {
                String name = mountain.path("mntiname").asText();
                String area = mountain.path("mntiadd").asText();
                String height = mountain.path("mntihigh").asText();

                System.out.println("산 이름: " + name);
                System.out.println("지역: " + area);
                System.out.println("높이: " + height);

                assertThat(name).isNotBlank();
                assertThat(area).isNotBlank();
                assertThat(height).isNotBlank();
            }
        }
    }
}
