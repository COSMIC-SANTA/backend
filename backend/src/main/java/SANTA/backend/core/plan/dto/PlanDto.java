package SANTA.backend.core.plan.dto;

import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.mountain.dto.CafeDTO;
import SANTA.backend.core.mountain.dto.RestaurantDTO;
import SANTA.backend.core.mountain.dto.StayDTO;
import SANTA.backend.core.mountain.dto.TouristSpotDTO;
import SANTA.backend.core.plan.entity.PlanEntity;

import java.time.LocalDateTime;
import java.util.List;

public record PlanDto(
        CompleteMountainDTO mountainDTO,
        List<RestaurantDTO> restaurantDTOS,
        List<StayDTO> stayDTOS,
        List<CafeDTO> cafeDTOS,
        List<TouristSpotDTO> spotDTOS,
        LocalDateTime targetDate
) {
    public static PlanDto fromEntity(PlanEntity planEntity){
        String name = planEntity.getCourse().getMountainEntity().getName();
        Difficulty difficulty = planEntity.getCourse().getMountainEntity().getDifficulty();
        CompleteMountainDTO completeMountainDTO = new CompleteMountainDTO(name, difficulty);
        List<RestaurantDTO> restaurantDTOS1 = planEntity.getCourse().getRestaurants().stream().map(restaurantEntity -> new RestaurantDTO(restaurantEntity.getName(), restaurantEntity.getLocation(), restaurantEntity.getImageUrl(), null, null)).toList();
        List<StayDTO> stayDTOS1 = planEntity.getCourse().getStays().stream().map(stayEntity -> new StayDTO(stayEntity.getName(), stayEntity.getLocation(), stayEntity.getImageUrl(), null, null)).toList();
        List<CafeDTO> cafeDTOS1 = planEntity.getCourse().getCafes().stream().map(cafeEntity -> new CafeDTO(cafeEntity.getName(), cafeEntity.getLocation(), cafeEntity.getImageUrl(), null, null)).toList();
        List<TouristSpotDTO> spotDTOS1 = planEntity.getCourse().getSpots().stream().map(spotEntity -> new TouristSpotDTO(spotEntity.getName(), spotEntity.getLocation(), spotEntity.getImageUrl(), null, null)).toList();
        return new PlanDto(completeMountainDTO, restaurantDTOS1, stayDTOS1, cafeDTOS1, spotDTOS1, planEntity.getTargetDate());
    }
}
