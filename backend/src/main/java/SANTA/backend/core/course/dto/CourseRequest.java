package SANTA.backend.core.course.dto;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;

import java.util.List;

public record CourseRequest(
        Mountain mountain,
        List<Cafe> cafes,
        List<Restaurant> restaurants,
        List<Stay> stays,
        List<Spot> spots

) {

}
