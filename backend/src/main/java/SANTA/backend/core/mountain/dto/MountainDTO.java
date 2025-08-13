package SANTA.backend.core.mountain.dto;

public record MountainDTO(
        String mountainName, // 산 이름
        String mountainAddress, // 산 주소
        String imageUrl,
        Double mapX, // 경도
        Double mapY // 위도
) {
}
