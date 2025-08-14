package SANTA.backend.core.mountain.dto;

public record CafeDTO(
        String name,
        String location,
        String imageUrl,
        Double mapX,
        Double mapY
) {
}
