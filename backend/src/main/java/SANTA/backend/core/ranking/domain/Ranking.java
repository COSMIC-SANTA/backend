package SANTA.backend.core.ranking.domain;

import SANTA.backend.core.ranking.entity.RankingEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Ranking {

    private Long id;

    private Long recordId;

    private Long rankValue;

    private RankingType rankingType;

    @Builder
    protected Ranking(Long id, Long recordId, Long rank, RankingType rankingType) {
        this.id = id;
        this.recordId = recordId;
        this.rankValue = rank;
        this.rankingType = rankingType;
    }

    public static Ranking fromEntity(RankingEntity rankingEntity) {
        return Ranking.builder()
                .id(rankingEntity.getRankingId())
                .recordId(rankingEntity.getRecord().getRecordId())
                .rank(rankingEntity.getRankValue())
                .rankingType(rankingEntity.getRankingType())
                .build();
    }
}
