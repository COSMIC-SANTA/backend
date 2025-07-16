package SANTA.backend.core.mountain.dto;

public record MountainNearByResponse(
        TouristSpotDTO touristSpotDTO,
        RestaurantDTO restaurantDTO,
        CafeDTO cafeDTO,
        StayDTO stayDTO
) {
}
