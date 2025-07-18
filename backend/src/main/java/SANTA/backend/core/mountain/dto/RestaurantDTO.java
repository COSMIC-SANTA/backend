package SANTA.backend.core.mountain.dto;

public record RestaurantDTO(
        Long id,
        String name,
        String location,
        String imageUrl,
        Long sequence
) {
}
