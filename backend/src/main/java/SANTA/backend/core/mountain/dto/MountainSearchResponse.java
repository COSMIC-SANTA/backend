package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.mountain.dto.external.ForestApiItem;

public record MountainSearchResponse(
        String mountainName,
        String mountainCode, // 산정보 코드
        String mountainAddress // 산정보 소재지
) {
    public static MountainSearchResponse from(ForestApiItem item) {
        return new MountainSearchResponse(
                item.mntiname(),
                item.mntilistno(),
                item.mntiadd()
        );
    }
}

