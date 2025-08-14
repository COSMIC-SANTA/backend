package SANTA.backend.core.mountain.dto;

public record StayDTO(
        String name,
        String location,
        String imageUrl,
        Double mapX,
        Double mapY
) {
}
