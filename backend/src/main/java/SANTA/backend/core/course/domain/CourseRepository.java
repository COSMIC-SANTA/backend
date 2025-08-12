package SANTA.backend.core.course.domain;

import SANTA.backend.core.course.entity.CourseEntity;

public interface CourseRepository {
    Long saveCourse(CourseEntity courseEntity);
}
