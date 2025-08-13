package SANTA.backend.core.course.domain;

import SANTA.backend.core.course.entity.CourseEntity;

import java.util.Optional;

public interface CourseRepository {
    Long saveCourse(CourseEntity courseEntity);

    Optional<CourseEntity> findById(Long courseId);
}
