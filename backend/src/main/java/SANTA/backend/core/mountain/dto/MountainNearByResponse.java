package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;

import java.util.List;

public record MountainNearByResponse(
        List<TouristSpotDTO> touristSpotDTO,
        List<RestaurantDTO> restaurantDTO,
        List<CafeDTO> cafeDTO,
        List<StayDTO> stayDTO
) {
    public static MountainNearByResponse fromDomain(List<Spot> spots, List<Restaurant> restaurants, List<Cafe> cafes, List<Stay> stays){
        List<TouristSpotDTO> spotDTOS = spots.stream().map(spot -> new TouristSpotDTO(spot.getId(), spot.getName(), spot.getLocation(), spot.getImageUrl(), spot.getSequence())).toList();
        List<RestaurantDTO> restaurantDTOS = restaurants.stream().map(restaurant -> new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getLocation(), restaurant.getImageUrl(), restaurant.getSequence())).toList();
        List<CafeDTO> cafeDTOS = cafes.stream().map(cafe -> new CafeDTO(cafe.getId(), cafe.getName(), cafe.getLocation(), cafe.getImageUrl(), cafe.getSequence())).toList();
        List<StayDTO> stayDTOS = stays.stream().map(stay -> new StayDTO(stay.getId(), stay.getName(), stay.getLocation(), stay.getImageUrl(), stay.getSequence())).toList();
        return new MountainNearByResponse(spotDTOS,restaurantDTOS,cafeDTOS,stayDTOS);
    }
}
