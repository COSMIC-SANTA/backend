package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.basePlace.domain.BasePlace;
import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;

import java.util.List;

public record OptimalRouteRequest(
        Position origin,
        BasePlace destination,
        List<Cafe> cafes,
        List<Restaurant> restaurants,
        List<Stay> stays,
        List<Spot> spots
) {
}
