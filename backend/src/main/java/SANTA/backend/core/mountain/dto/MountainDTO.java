package SANTA.backend.core.mountain.dto;

import SANTA.backend.core.basePlace.domain.Position;

public record MountainDTO(
        String mountainName, // 산 이름
        String mountainAddress, // 산 주소
        Position position
) {
}

