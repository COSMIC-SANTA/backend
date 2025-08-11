package SANTA.backend.core.mountain.dto;

public record MountainFacilityRequest(
        String mountainName,
        String mapX, // 경도
        String mapY // 위도
) {
}
