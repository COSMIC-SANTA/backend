package SANTA.backend.core.mountain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public record ForestApiItems(
        @JsonDeserialize(using = FlexibleItemDeserializer.class)
        List<ForestApiItem> item
) {
}
