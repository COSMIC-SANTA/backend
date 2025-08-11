package SANTA.backend.core.basePlace.domain;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.stay.domain.Stay;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Cafe.class, name = "cafe"),
        @JsonSubTypes.Type(value = Stay.class, name = "stay"),
        @JsonSubTypes.Type(value = Spot.class, name = "spot"),
        @JsonSubTypes.Type(value = Restaurant.class, name = "restaurant")
})

public abstract class BasePlace {
    protected Long id;

    protected Long courseId;

    protected Long sequence;

    protected String name;

    protected String location;

    protected String imageUrl;

    protected Position position;

    protected BasePlace(Long id, Long courseId, Long sequence, String name, String location, String imageUrl, Position position) {
        this.id = id;
        this.courseId = courseId;
        this.sequence = sequence;
        this.name = name;
        this.location = location;
        this.imageUrl = imageUrl;
        this.position = position;
    }
}
