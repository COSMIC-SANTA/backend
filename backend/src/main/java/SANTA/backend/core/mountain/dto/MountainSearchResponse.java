package SANTA.backend.core.mountain.dto;

import java.util.List;

public record MountainSearchResponse(
        List<MountainDTO> mountains
) {
}

