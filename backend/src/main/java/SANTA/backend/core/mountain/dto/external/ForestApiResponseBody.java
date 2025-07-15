package SANTA.backend.core.mountain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForestApiResponseBody(
        ForestApiHeader header,
        ForestApiBodyContent body
) {
}
