package SANTA.backend.core.cafe.entity;

import SANTA.backend.core.basePlace.entity.BasePlaceEntity;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "CAFE")
public class CafeEntity extends BasePlaceEntity {

    @Builder
    protected CafeEntity(Long id, CourseEntity courseEntity, Long sequence, String name, String location, String imageUrl) {
        super(id, courseEntity, sequence, name, location, imageUrl);
    }

    public static CafeEntity from(Cafe cafe, CourseEntity courseEntity) {
        return CafeEntity.builder()
                .id(cafe.getId())
                .courseEntity(courseEntity)
                .sequence(cafe.getSequence())
                .name(cafe.getName())
                .location(cafe.getLocation())
                .imageUrl(cafe.getImageUrl())
                .build();
    }
}
