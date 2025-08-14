package SANTA.backend.core.mountain.dto;

public record FacilityDTO(
        String placeName, // 편의시설 이름
        String addressName, // 주소 이름
        String mapX, // 경도
        String mapY, // 위도
        String distance
) {
}
