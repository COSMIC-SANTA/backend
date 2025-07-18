package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.TouristApiResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface APIRequester {

    public Mono<String> getContentByAreaCode2(int areaCode, String areaName, int rnum);

    public Mono<String> getContentByCategoryCode2();

    public Mono<String> getContentByAreaBasedList2();

    public Mono<String> getContentByLocationBasedList2(Long mapX, Long mapY, Long radius);

    public Mono<String> getContentBySearchKeyword2(String keyword, int areaCode, int sigunguCode);

    public Mono<String> getContentBySearchFestival2(LocalDateTime eventStartDate, LocalDateTime eventEndDate);

    public Mono<String> getContentBySearchStay2(int areaCode, String areaName);

    public Mono<String> getContentByDetailCommon2(int contentId);

    public Mono<String> getContentByDetailIntro2(int contentId, int contentTypeId);

    public Mono<String> getContentByDetailInfo2(int contentId, int contentTypeId);

    public Mono<String> getContentByDetailImage2(int contentId);

    public Mono<String> getContentByAreaBasedSyncList2();

    public Mono<String> getContentByDetailPetTour2();

    public Mono<String> getContentByLdongCode2();

    public Mono<String> getContentByLclsSystemCode2();
}
