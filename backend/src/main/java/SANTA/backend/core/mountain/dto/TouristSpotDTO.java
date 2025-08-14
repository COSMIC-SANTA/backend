package SANTA.backend.core.mountain.dto;

public record TouristSpotDTO(
        String name,
        String location,
        String imageUrl,
        Double mapX,
        Double mapY
) {

}
