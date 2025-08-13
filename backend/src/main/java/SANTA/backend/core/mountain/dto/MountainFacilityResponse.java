package SANTA.backend.core.mountain.dto;

import java.util.List;

public record MountainFacilityResponse(
        List<FacilityDTO> toilet,
        List<FacilityDTO> water,
        List<FacilityDTO> hospital,
        List<FacilityDTO> pharmacy
) {
}
