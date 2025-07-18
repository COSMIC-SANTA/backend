package SANTA.backend.core.mountain.dto;

public record TouristSpotDTO(
        Long id,
        String name,
        String location,
        String imageUrl,
        Long sequence,
        Double mapX,
        Double mapY
) {

}
