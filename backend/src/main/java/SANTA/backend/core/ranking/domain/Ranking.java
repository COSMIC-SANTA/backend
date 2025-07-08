package SANTA.backend.core.ranking.domain;

import SANTA.backend.core.ranking.entity.RankingEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Ranking {

    private Long id;

    private Long recordId;

    private Long rank;

    private RankingType rankingType;

    @Builder
    protected Ranking(Long id, Long recordId, Long rank, RankingType rankingType) {
        this.id = id;
        this.recordId = recordId;
        this.rank = rank;
        this.rankingType = rankingType;
    }

    public static Ranking fromEntity(RankingEntity rankingEntity) {
        return Ranking.builder()
                .id(rankingEntity.getRankingId())
                .recordId(rankingEntity.getRecord().getRecordId())
                .rank(rankingEntity.getRank())
                .rankingType(rankingEntity.getRankingType())
                .build();
    }
}
