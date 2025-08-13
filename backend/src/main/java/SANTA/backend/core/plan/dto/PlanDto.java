package SANTA.backend.core.plan.dto;

import SANTA.backend.core.mountain.domain.Difficulty;
import SANTA.backend.core.plan.entity.PlanEntity;

public record PlanDto(
        CompleteMountainDTO mountainDTO
) {
    public static PlanDto fromEntity(PlanEntity planEntity){
        String name = planEntity.getCourse().getMountainEntity().getName();
        Difficulty difficulty = planEntity.getCourse().getMountainEntity().getDifficulty();
        CompleteMountainDTO completeMountainDTO = new CompleteMountainDTO(name, difficulty);
        return new PlanDto(completeMountainDTO);
    }
}
