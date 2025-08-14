package SANTA.backend.core.banner.dto;

public record BannerDescriptionDTO(
        String mountainName,
        String high,
        String mntidetails, // 산 정보 상세설명
        String mntitop // 개관
) {
}

