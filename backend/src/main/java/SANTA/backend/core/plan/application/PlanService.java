package SANTA.backend.core.plan.application;

import SANTA.backend.core.basePlace.domain.Position;
import SANTA.backend.core.cafe.domain.Cafe;
import SANTA.backend.core.cafe.entity.CafeEntity;
import SANTA.backend.core.course.entity.CourseEntity;
import SANTA.backend.core.mountain.domain.Mountain;
import SANTA.backend.core.mountain.dto.*;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.plan.domain.Plan;
import SANTA.backend.core.plan.domain.PlanRepository;
import SANTA.backend.core.plan.domain.PlanState;
import SANTA.backend.core.plan.dto.PlanDto;
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
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
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
    public Long savePlan(Long userId, LocalDateTime targetDate, Mountain mountain, List<Restaurant> restaurants, List<Stay> stays, List<Cafe> cafes, List<Spot> spots) {
        UserEntity user = userRepository.findEntityById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "해당되는 유저를 찾지 못했습니다."));
        CourseEntity course = makeCourse(mountain, restaurants, stays, cafes, spots);
        PlanEntity plan = PlanEntity.builder()
                .course(course)
                .targetDate(targetDate)
                .state(PlanState.IN_PROGRESS)
                .user(user)
                .build();
        return planRepository.savePlan(plan);
    }

    @Transactional
    public List<PlanDto> getCompletedPlans(Long userId){
        PlanState planState = PlanState.COMPLETED;
        List<PlanEntity> completedPlans = planRepository.findPlans(userId, planState);
        return completedPlans.stream().map(PlanDto::fromEntity).toList();
    }

    @Transactional
    public List<PlanDto> getNotCompletedPlans(Long userId){
        PlanState planState = PlanState.IN_PROGRESS;
        List<PlanEntity> notCompletedPlans = planRepository.findPlans(userId,planState);
        return notCompletedPlans.stream().map(PlanDto::fromEntity).toList();
    }

    private static CourseEntity makeCourse(Mountain mountainDTO, List<Restaurant> restaurantDtos, List<Stay> stayDTOS, List<Cafe> cafeDtos, List<Spot> spotDTOS) {
        CourseEntity course = CourseEntity.builder().build();

        List<CafeEntity> cafeEntities = cafeDtos != null ?
                parseCafeDto(cafeDtos).stream().map(CafeEntity::from).toList() :
                Collections.emptyList();

        List<RestaurantEntity> restaurantEntities = restaurantDtos != null ?
                parseRestaurantDto(restaurantDtos).stream().map(RestaurantEntity::from).toList() :
                Collections.emptyList();

        List<SpotEntity> spotEntities = spotDTOS != null ?
                parseSpotDto(spotDTOS).stream().map(SpotEntity::from).toList() :
                Collections.emptyList();

        List<StayEntity> stayEntities = stayDTOS != null ?
                parseStayDto(stayDTOS).stream().map(StayEntity::from).toList() :
                Collections.emptyList();

        Mountain mountain = parseMountainDto(mountainDTO);
        MountainEntity mountainEntity = MountainEntity.from(mountain);

        course.setLocations(mountainEntity, cafeEntities, restaurantEntities, spotEntities, stayEntities);
        return course;
    }

    private static Mountain parseMountainDto(Mountain mountainDTO) {
        Mountain mountain = Mountain.builder()
                .name(mountainDTO.getName())
                .location(mountainDTO.getLocation())
                .imageUrl(mountainDTO.getImageUrl())
                .position(new Position(mountainDTO.getPosition().getMapX(), mountainDTO.getPosition().getMapY()))
                .build();
        return mountain;
    }

    private static List<Stay> parseStayDto(List<Stay> stayDTOS) {
        List<Stay> stays = stayDTOS.stream().map(stayDTO -> Stay.builder()
                .name(stayDTO.getName())
                .location(stayDTO.getLocation())
                .imageUrl(stayDTO.getImageUrl())
                .position(new Position(stayDTO.getPosition().getMapX(), stayDTO.getPosition().getMapY()))
                .build()).toList();
        return stays;
    }

    private static List<Spot> parseSpotDto(List<Spot> spotDTOS) {
        List<Spot> spots = spotDTOS.stream().map(spotDTO -> Spot.builder()
                .name(spotDTO.getName())
                .location(spotDTO.getLocation())
                .imageUrl(spotDTO.getImageUrl())
                .position(new Position(spotDTO.getPosition().getMapX(), spotDTO.getPosition().getMapY()))
                .build()).toList();
        return spots;
    }

    private static List<Restaurant> parseRestaurantDto(List<Restaurant> restaurantDtos) {
        List<Restaurant> restaurants = restaurantDtos.stream().map(restaurantDTO -> Restaurant.builder()
                .name(restaurantDTO.getName())
                .location(restaurantDTO.getLocation())
                .imageUrl(restaurantDTO.getImageUrl())
                .position(new Position(restaurantDTO.getPosition().getMapX(), restaurantDTO.getPosition().getMapY()))
                .build()).toList();
        return restaurants;
    }

    private static List<Cafe> parseCafeDto(List<Cafe> cafeDtos) {
        List<Cafe> cafes = cafeDtos.stream().map(cafeDTO -> Cafe.builder()
                .name(cafeDTO.getName())
                .location(cafeDTO.getLocation())
                .imageUrl(cafeDTO.getImageUrl())
                .position(new Position(cafeDTO.getPosition().getMapX(), cafeDTO.getPosition().getMapY()))
                .build()).toList();
        return cafes;
    }
}

