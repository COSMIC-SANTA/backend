package SANTA.backend.core.course.infra;

import SANTA.backend.core.course.domain.CourseRepository;
import SANTA.backend.core.course.entity.CourseEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CourseJpaRepository implements CourseRepository {

    private final EntityManager em;

    @Override
    public Long saveCourse(CourseEntity courseEntity) {
        em.persist(courseEntity);
        return courseEntity.getId();
    }
}
