package SANTA.backend.core.mountain.dto;

public record MountainDTO(
        String mountainName, // 산 이름
        String mountainAddress, // 산 주소
        String mapX, // 경도
        String mapY // 위도
) {
}
