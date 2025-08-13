package SANTA.backend.core.plan.dto;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;

import java.time.LocalDateTime;
import java.util.List;

public record PlanRequest(
        Mountain mountain,
        LocalDateTime targetDate,
        List<Cafe> cafes,
        List<Restaurant> restaurants,
        List<Stay> stays,
        List<Spot> spots

) {

}
