package SANTA.backend.core.mountain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForestApiBodyContent(
        ForestApiItems items,
        int numOfRows,
        int pageNo,
        int totalCount
) {
}
