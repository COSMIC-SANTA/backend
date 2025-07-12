package SANTA.backend.core.traffic.domain;

import SANTA.backend.core.traffic.entity.TrafficEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Traffic {

    private Long id;

    private TrafficType type;

    private Long estimatedTime; // 예상 소요 시간

    @Builder
    protected Traffic(Long id, TrafficType type, Long estimatedTime) {
        this.id = id;
        this.type = type;
        this.estimatedTime = estimatedTime;
    }

    public static Traffic fromEntity(TrafficEntity trafficEntity) {
        return Traffic.builder()
                .id(trafficEntity.getId())
                .type(trafficEntity.getType())
                .estimatedTime(trafficEntity.getEstimatedTime())
                .build();
    }
}
