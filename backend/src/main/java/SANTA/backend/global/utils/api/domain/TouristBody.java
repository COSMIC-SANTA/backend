package SANTA.backend.global.utils.api.domain;

public record TouristBody(
        TouristItems items,
        int numOfRows,
        int pageNo,
        int totalCount
) {
}
