package SANTA.backend.core.mountain.dto.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ForestApiItem(
        String mntiadd,        // 산 소재지
        String mntiadmin,      // 관리기관
        String mntiadminnum,   // 관리기관 연락처
        String mntidetails,    // 상세설명
        Double mntihigh,       // 높이
        String mntilistno,     // 산정보 코드
        String mntiname,       // 산 이름
        String mntinfdt,       // 정보 갱신일
        String mntisname,      // 산 별명
        String mntisummary,    // 요약
        String mntitop         // 정상 정보
) {
}
