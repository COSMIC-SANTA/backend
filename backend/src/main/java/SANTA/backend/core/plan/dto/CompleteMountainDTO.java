package SANTA.backend.core.plan.dto;

import SANTA.backend.core.mountain.domain.Difficulty;

public record CompleteMountainDTO(
        String name,
        Difficulty difficulty
) {
}
