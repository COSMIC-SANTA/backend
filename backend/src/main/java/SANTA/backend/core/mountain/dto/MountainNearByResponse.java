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
    public static MountainNearByResponse fromDomain(List<Restaurant> restaurants, List<Stay> stays, List<Cafe> cafes, List<Spot> spots){
        List<TouristSpotDTO> spotDTOS = spots.stream().map(spot -> new TouristSpotDTO(spot.getId(), spot.getName(), spot.getLocation(), spot.getImageUrl(), spot.getSequence(), spot.getPosition().getMapX(), spot.getPosition().getMapY())).toList();
        List<RestaurantDTO> restaurantDTOS = restaurants.stream().map(restaurant -> new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getLocation(), restaurant.getImageUrl(), restaurant.getSequence(), restaurant.getPosition().getMapX(), restaurant.getPosition().getMapY())).toList();
        List<CafeDTO> cafeDTOS = cafes.stream().map(cafe -> new CafeDTO(cafe.getId(), cafe.getName(), cafe.getLocation(), cafe.getImageUrl(), cafe.getSequence(), cafe.getPosition().getMapX(), cafe.getPosition().getMapY())).toList();
        List<StayDTO> stayDTOS = stays.stream().map(stay -> new StayDTO(stay.getId(), stay.getName(), stay.getLocation(), stay.getImageUrl(), stay.getSequence(), stay.getPosition().getMapY(), stay.getPosition().getMapY())).toList();
        return new MountainNearByResponse(spotDTOS,restaurantDTOS,cafeDTOS,stayDTOS);
    }
}
