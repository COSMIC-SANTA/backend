package SANTA.backend.mountain.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.MountainFacilityRequest;
import SANTA.backend.core.mountain.dto.MountainFacilityResponse;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MountainServiceTest extends ServiceContext {

    @Test
    @Transactional
    void 산_주변_관광지를_조회할_수_있다() {
        //given
        String name = "산인데용";
        String location = "충청북도 청주";

        Mountain mountain = Mountain.builder()
                .name(name)
                .location(location)
                .difficulty(Difficulty.EXTREME)
                .build();

        mountainService.saveMountain(mountain);
        List<Mountain> mountains = mountainService.findByName(name);

        //when
        MountainNearByResponse mountainNearByResponse = mountainService.searchNearByPlacesById(mountains.get(0).getId(),1L);
        System.out.println(mountainNearByResponse);

        //then
        Assertions.assertThat(mountainNearByResponse).isNotNull();
    }

    @Nested
    class 카카오_편의시설_검색_테스트 {

        @Test
        void 산_주변의_편의시설을_검색할_수_있다() {
            // given
            String mapX = "127.73062186929766";
            String mapY = "35.33691118099147";
            MountainFacilityRequest request = new MountainFacilityRequest(mapX, mapY);

            // when
            MountainFacilityResponse response = mountainService.searchFacilities(request);
            System.out.println("지리산 편의시설 검색 결과: " + response);

            //then
            assertThat(response).isNotNull();
            assertThat(response.hospital()).isNotNull();
            assertThat(response.pharmacy()).isNotNull();
            assertThat(response.toilet()).isNotNull();
            assertThat(response.water()).isNotNull();

            System.out.println("화장실 검색 결과 수: " + response.toilet().size());
            System.out.println("음수대 검색 결과 수: " + response.water().size());
            System.out.println("병원 검색 결과 수: " + response.hospital().size());
            System.out.println("약국 검색 결과 수: " + response.pharmacy().size());
        }
    }
}
