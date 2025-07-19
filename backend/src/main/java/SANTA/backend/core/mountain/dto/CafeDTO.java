package SANTA.backend.core.mountain.dto;

public record CafeDTO(
        Long id,
        String name,
        String location,
        String imageUrl,
        Long sequence,
        Double mapX,
        Double mapY
) {
}
