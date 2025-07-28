package SANTA.backend.course.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.stay.domain.Stay;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KaKaoMapTest extends ServiceContext {

    @Nested
    class 카카오_맵_응답_테스트 {

        @Test
        void 카카오_맵_api가_작동한다() {
            //given
            Double mapX = 127.10764191124568;
            Double mapY = 37.402464820205246;

            Position origin = new Position(mapX, mapY);

            List<Cafe> cafes = new ArrayList<>();

            Double cafeMapX = 127.17354989857544;
            Double cafeMapY = 37.36629687436494;
            Position cafePosition = new Position(cafeMapX, cafeMapY);
            Cafe cafe = Cafe.builder().position(cafePosition).build();
            cafes.add(cafe);

            Position destinationPosition = new Position(127.11056336672839, 37.39419693653072);
            BasePlace destination = Stay.builder().position(destinationPosition).build();


            //when
            JsonNode block = kaKaoMapServiceRequester.searchRoute(origin, cafes, null, null, null, destination).block();
            System.out.println(block);
            //then
            assertThat(block).isNotNull();
        }
    }
}
