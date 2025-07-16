package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.utils.api.domain.MobileOS;
import SANTA.backend.global.utils.api.domain.Operation;
import SANTA.backend.global.utils.api.domain.TouristApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class KoreanTourInfoServiceRequester implements APIRequester {

    private final AppProperties appProperties;
    private final WebClientService webClientService;


    private Mono<String> getContent(Operation operation){
        String url = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceURL();
        String key = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceEncodingKey();
        String mobileOs = MobileOS.WEB.name();
        String mobileAppName = appProperties.getKoreaTourOrganization().getMobileAppName();

        return webClientService.request(url,operation.name(), key, mobileOs, mobileAppName);
    }

    /**
     *
     * @param areaCode 지역코드또는시군구코드
     * @param areaName 지역명또는시군구명
     * @param rnum 일련번호
     * @return
     */
    @Override
    public Mono<String> getContentByAreaCode2(int areaCode, String areaName, int rnum) {
        return getContent(Operation.areaCode2);
    }

    @Override
    public Mono<String> getContentByCategoryCode2() {
        return getContent(Operation.categoryCode2);
    }

    /**
     * 지역기반관광정보조회
     *
     * @return
     */
    @Override
    public Mono<String> getContentByAreaBasedList2() {
        return getContent(Operation.areaBasedList2);
    }

    /**
     * 위치기반관광정보조회
     * @param mapX x좌표
     * @param mapY y좌표
     * @param radius 거리 반경
     * @return
     */
    @Override
    public Mono<String> getContentByLocationBasedList2(Long mapX, Long mapY, Long radius){
        return getContent(Operation.locationBasedList2);
    }



    @Override
    public Mono<String> getContentBySearchKeyword2(String keyword, int areaCode, int sigunguCode){
        return getContent(Operation.searchKeyword2);
    }

    @Override
    public Mono<String> getContentBySearchFestival2(LocalDateTime eventStartDate, LocalDateTime eventEndDate){
        String formattedEventStartDate = eventStartDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEventEndDate = eventEndDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return getContent(Operation.searchFestival2);
    }


    @Override
    public Mono<String> getContentBySearchStay2(int areaCode, String areaName){
        return getContent(Operation.searchStay2);
    }

    /**
     * 타입별 공통정보(기본정보, 약도 이미지, 대표 이미지, 분류, 지역 정보, 주소, 좌표, 개요정보, 길안내 정보, 연계관광정보 등)를 조회하는기능입니다.
     * @param contentId
     * @return
     */
    @Override
    public Mono<String> getContentByDetailCommon2(int contentId){
        return getContent(Operation.detailCommon2);
    }

    /**
     * 타입 별 소개정보(휴무일, 개장시간, 주차시설 등)를 조회하는 기능입니다.
     * 각 타입마다 응답 항목이 다르게 제공됩니다.
     * @param contentId
     * @param contentTypeId
     * @return
     */
    @Override
    public Mono<String> getContentByDetailIntro2(int contentId, int contentTypeId){
        return getContent(Operation.detailIntro2);
    }

    /**
     * "숙박”은 객실정보를 제공합니다.
     * “여행코스”는 코스정보를 제공합니다.
     * “숙박”, “여행코스”를 제외한 나머지 타입은 다양한 정보를 반복적인 형태로 제공합니다.
     * @param contentId
     * @param contentTypeId
     * @return
     */
    @Override
    public Mono<String> getContentByDetailInfo2(int contentId, int contentTypeId){
        return getContent(Operation.detailInfo2);
    }

    @Override
    public Mono<String> getContentByDetailImage2(int contentId){
        return getContent(Operation.detailImage2);
    }

    @Override
    public Mono<String> getContentByAreaBasedSyncList2(){
        return getContent(Operation.areaBasedSyncList2);
    }

    @Override
    public Mono<String> getContentByDetailPetTour2(){
        return getContent(Operation.detailPetTour2);
    }

    @Override
    public Mono<String> getContentByLdongCode2(){
        return getContent(Operation.ldongCode2);
    }

    @Override
    public Mono<String> getContentByLclsSystemCode2(){
        return getContent(Operation.lclsSystmCode2);
    }

}
