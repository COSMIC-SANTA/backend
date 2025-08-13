package SANTA.backend.core.plan.application;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.cafe.entity.CafeEntity;
import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.*;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.plan.domain.PlanRepository;
import SANTA.backend.core.plan.domain.PlanState;
import SANTA.backend.core.plan.entity.PlanEntity;
import SANTA.backend.core.restaurant.domain.Restaurant;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import SANTA.backend.core.spot.domain.Spot;
import SANTA.backend.core.spot.entity.SpotEntity;
import SANTA.backend.core.stay.domain.Stay;
import SANTA.backend.core.stay.entity.StayEntity;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long completePlan(Long planId, Long distance) {
        PlanEntity plan = planRepository.findById(planId)
                .orElseThrow(() -> new CustomException(ErrorCode.MOUNTAIN_NOT_FOUND, "해당되는 플랜이 존재하지 않습니다."));
        CourseEntity course = plan.getCourse();
        course.updateDistance(distance);
        plan.completePlan();
        return plan.getId();
    }

    @Transactional
    public Long savePlan(Long userId, LocalDateTime targetDate, MountainDTO mountain, List<RestaurantDTO> restaurants, List<StayDTO> stays, List<CafeDTO> cafes, List<TouristSpotDTO> spots) {
        UserEntity user = userRepository.findEntityById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "해당되는 유저를 찾지 못했습니다."));
        CourseEntity course = makeCourse(mountain, restaurants, stays, cafes, spots);
        PlanEntity plan = PlanEntity.builder()
                .course(course)
                .targetDate(targetDate)
                .state(PlanState.PLANNED)
                .user(user)
                .build();
        return planRepository.savePlan(plan);
    }

    private static CourseEntity makeCourse(MountainDTO mountain, List<RestaurantDTO> restaurants, List<StayDTO> stays, List<CafeDTO> cafes, List<TouristSpotDTO> spots) {
        CourseEntity course = CourseEntity.builder().build();
//        cafes.stream().map(cafeDTO -> Cafe.builder().name(cafeDTO.name()).location(cafeDTO.location()).imageUrl(cafeDTO.imageUrl()).position(new Position(cafeDTO.mapX(), cafeDTO.mapY())));
//        List<CafeEntity> cafeEntities = cafes.stream().map(CafeEntity::from).toList();
//        List<RestaurantEntity> restaurantEntities = restaurants.stream().map(RestaurantEntity::from).toList();
//        List<SpotEntity> spotEntities = spots.stream().map(SpotEntity::from).toList();
//        List<StayEntity> stayEntities = stays.stream().map(StayEntity::from).toList();
//        MountainEntity mountainEntity = MountainEntity.from(mountain);
//        course.setLocations(mountainEntity, cafeEntities, restaurantEntities, spotEntities, stayEntities);
        return course;
    }
}
