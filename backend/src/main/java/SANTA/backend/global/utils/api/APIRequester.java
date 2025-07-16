package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.AreaCode;
import SANTA.backend.global.utils.api.domain.Arrange;
import SANTA.backend.global.utils.api.domain.ContentTypeId;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface APIRequester {

    Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo);

    Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo, AreaCode areaCode);

    Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode);

    Mono<String> getContentByAreaBasedList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Arrange arrange, ContentTypeId contentTypeId);

    Mono<String> getContentByLocationBasedList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Long radius, Double mapX, Double mapY);

    Mono<String> getContentBySearchKeyword2(Long numOfRows, Long pageNo, String keyword, AreaCode areaCode, Long sigunguCode, Arrange arrange);

    Mono<String> getContentBySearchFestival2(Long numOfRows, Long pageNo, LocalDateTime eventStartDate, LocalDateTime eventEndDate, Arrange arrange, AreaCode areaCode, Long sigunguCode);

    Mono<String> getContentBySearchStay2(Long numOfRows, Long pageNo, Arrange arrange, AreaCode areaCode, Long sigunguCode);

    Mono<String> getContentByDetailCommon2(Long numOfRows, Long pageNo, Long contentId);

    Mono<String> getContentByDetailIntro2(Long numOfRows, Long pageNo, long contentId, ContentTypeId contentTypeId);

    Mono<String> getContentByDetailInfo2(Long numOfRows, Long pageNo, long contentId, ContentTypeId contentTypeId);

    Mono<String> getContentByDetailImage2(Long numOfRows, Long pageNo, Long contentId);

    Mono<String> getContentByAreaBasedSyncList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Arrange arrange, ContentTypeId contentTypeId);

    Mono<String> getContentByDetailPetTour2(Long numOfRows, Long pageNo, Long contentId);

    Mono<String> getContentByLdongCode2();

    Mono<String> getContentByLclsSystemCode2();
}
