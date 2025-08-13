package SANTA.backend.course.application;

import SANTA.backend.context.ServiceContext;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.domain.MountainRepository;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CourseServiceTest extends ServiceContext {

    @Test
    void 코스_저장을_할_수_있습니다(){
        //given
        Mountain mountain = Mountain.builder()
                .name("산")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .location("위치").build();
        List<Restaurant> restaurants = new ArrayList<>();
        restaurants.add(restaurant);

        Stay stay = Stay.builder()
                        .location("위치").build();
        List<Stay> stays = new ArrayList<>();
        stays.add(stay);

        Cafe cafe = Cafe.builder()
                .location("위치").build();
        List<Cafe> cafes = new ArrayList<>();
        cafes.add(cafe);

        Spot spot = Spot.builder()
                        .location("위치").build();
        List<Spot> spots = new ArrayList<>();
        spots.add(spot);

        //when
        Long courseId = courseService.saveCourse(mountain, restaurants, stays, cafes, spots);

        //then
        System.out.println(courseId);
        Assertions.assertThat(courseId).isNotNull();
    }


}