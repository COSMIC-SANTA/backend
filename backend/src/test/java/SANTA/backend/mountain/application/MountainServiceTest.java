package SANTA.backend.mountain.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.MountainNearByResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        MountainNearByResponse mountainNearByResponse = mountainService.searchNearByPlacesById(mountains.get(0).getId());
        System.out.println(mountainNearByResponse);

        //then
        Assertions.assertThat(mountainNearByResponse).isNotNull();
    }
}
