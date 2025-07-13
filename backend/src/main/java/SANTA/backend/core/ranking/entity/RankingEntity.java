package SANTA.backend.core.ranking.entity;

import SANTA.backend.core.ranking.domain.Ranking;
import SANTA.backend.core.ranking.domain.RankingType;
import SANTA.backend.core.record.entity.RecordEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "RANKING")
@NoArgsConstructor
public class RankingEntity {

    @Id @GeneratedValue
    private Long rankingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECORD_ID")
    private RecordEntity record;

    private Long rank;

    @Enumerated(EnumType.STRING)
    private RankingType rankingType;

    @Builder
    public RankingEntity(Long rankingId, RecordEntity record, RankingType rankingType, Long rank) {
        this.rankingId = rankingId;
        this.record = record;
        this.rank = rank;
        this.rankingType = rankingType;
    }

    public static RankingEntity from(Ranking ranking, RecordEntity record) {
        return RankingEntity.builder()
                .rankingId(ranking.getId())
                .record(record)
                .rank(ranking.getRank())
                .rankingType(ranking.getRankingType())
                .build();
    }

}
