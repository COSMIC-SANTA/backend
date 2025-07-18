package SANTA.backend.core.restaurant.infra;

import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.restaurant.domain.RestaurantRepository;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantJPARepository implements RestaurantRepository {

    private final EntityManager em;

    @Override
    public List<Restaurant> findByLocation(String location) {
        List<RestaurantEntity> restaurantEntities = em.createQuery("select r from RestaurantEntity r where r.location =: location", RestaurantEntity.class)
                .setParameter("location", location)
                .getResultList();
        return restaurantEntities.stream().map(Restaurant::fromEntity).toList();
    }
}
