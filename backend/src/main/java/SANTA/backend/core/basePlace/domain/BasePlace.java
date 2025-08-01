package SANTA.backend.core.basePlace.domain;

import lombok.Getter;

@Getter
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
