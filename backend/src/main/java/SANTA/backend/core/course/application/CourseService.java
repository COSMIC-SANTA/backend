package SANTA.backend.core.course.application;

import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.cafe.entity.CafeEntity;
import SANTA.backend.core.course.domain.CourseRepository;
import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.spot.entity.SpotEntity;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.stay.entity.StayEntity;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional
    public Long saveCourse(Mountain mountain, List<Restaurant> restaurants, List<Stay> stays, List<Cafe> cafes, List<Spot> spots) {
        CourseEntity course = CourseEntity.builder().build();
        List<CafeEntity> cafeEntities = cafes.stream().map(CafeEntity::from).toList();
        List<RestaurantEntity> restaurantEntities = restaurants.stream().map(RestaurantEntity::from).toList();
        List<SpotEntity> spotEntities = spots.stream().map(SpotEntity::from).toList();
        List<StayEntity> stayEntities = stays.stream().map(StayEntity::from).toList();
        MountainEntity mountainEntity = MountainEntity.from(mountain);
        course.makeCourse(mountainEntity, cafeEntities, restaurantEntities, spotEntities, stayEntities);
        return courseRepository.saveCourse(course);
    }

//    @Transactional
//    public Long updateCompleteCourse(Long courseId, Long distance) {
//        CourseEntity course  = courseRepository.findById(courseId)
//                .orElseThrow(()-> new CustomException(ErrorCode.MOUNTAIN_NOT_FOUND, "해당되는 산이 존재하지 않습니다."));
//        Long mountainId = course.getMountainEntity().getId();
//        MountainEntity mountain = mountainRepository.findById(mountainId)
//                .orElseThrow(()-> new CustomException(ErrorCode.MOUNTAIN_NOT_FOUND, "해당되는 산이 존재하지 않습니다."));
//        mountain.
//    }
}
