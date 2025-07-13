package SANTA.backend.core.mountain.dto;
import SANTA.backend.core.mountain.dto.external.ForestApiItem;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record MountainListSearchResponse(
        List<MountainSearchResponse> mountains
) {
    public static MountainListSearchResponse from(List<ForestApiItem> items) {
        List<MountainSearchResponse> mountains = IntStream.range(0, items.size())
                .mapToObj(i -> MountainSearchResponse.from(items.get(i)))
                .collect(Collectors.toList());

        return new MountainListSearchResponse(mountains);
    }
}
