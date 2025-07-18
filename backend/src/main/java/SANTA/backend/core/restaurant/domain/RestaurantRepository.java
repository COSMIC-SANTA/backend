package SANTA.backend.core.restaurant.domain;

import java.util.List;

public interface RestaurantRepository {
    public List<Restaurant> findByLocation(String location);
}
