package SANTA.backend.core.plan.dto;

import SANTA.backend.core.mountain.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public record PlanRequest(
        MountainRequestDTO mountain,
        LocalDateTime targetDate,
        List<TouristSpotDTO> touristSpotDTO,
        List<RestaurantDTO> restaurantDTO,
        List<CafeDTO> cafeDTO,
        List<StayDTO> stayDTO
) {

}
