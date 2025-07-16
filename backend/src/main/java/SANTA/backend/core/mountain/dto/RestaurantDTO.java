package SANTA.backend.core.mountain.dto;

public record RestaurantDTO(
        Long id,
        String name,
        String location,
        String image_url,
        int sequence
) {
}
