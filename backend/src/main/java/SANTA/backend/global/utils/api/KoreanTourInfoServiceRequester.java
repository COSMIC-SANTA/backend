package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.utils.api.domain.*;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class KoreanTourInfoServiceRequester extends KoreanTourInfoServiceURIGenerator implements APIRequester {

    private final AppProperties appProperties;
    private final WebClientService webClientService;

    @Override
    public Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo) {
        return getContent(Operation.areaCode2, numOfRows, pageNo, null, null, null, null, null, null, null, null, null, null,null);
    }

    @Override
    public Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo, AreaCode areaCode) {
        return getContent(Operation.areaCode2, numOfRows, pageNo, null, null, areaCode, null, null, null, null, null, null, null,null);
    }

    @Override
    public Mono<String> getContentByAreaCode2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode) {
        return getContent(Operation.areaCode2, numOfRows, pageNo, null, null, areaCode, sigunguCode, null, null, null, null, null, null,null);
    }

    @Override
    public Mono<String> getContentByAreaBasedList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Arrange arrange, ContentTypeId contentTypeId) {
        return getContent(Operation.areaBasedList2, numOfRows, pageNo, arrange, contentTypeId, areaCode, sigunguCode, null, null, null, null, null, null,null);
    }

    @Override
    public Mono<String> getContentByLocationBasedList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Long radius, Double mapX, Double mapY) {
        return getContent(Operation.locationBasedList2, numOfRows, pageNo, null, null, areaCode, sigunguCode, radius, mapX, mapY, null, null, null,null);
    }

    @Override
    public Mono<String> getContentBySearchKeyword2(Long numOfRows, Long pageNo, String keyword, AreaCode areaCode, Long sigunguCode, Arrange arrange) {
        return getContent(Operation.searchKeyword2, numOfRows, pageNo, arrange, null, areaCode, sigunguCode, null, null, null, keyword, null, null,null);
    }

    @Override
    public Mono<String> getContentBySearchFestival2(Long numOfRows, Long pageNo, LocalDateTime eventStartDate, LocalDateTime eventEndDate, Arrange arrange, AreaCode areaCode, Long sigunguCode) {
        String formattedEventStartDate = eventStartDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String formattedEventEndDate = eventEndDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return getContent(Operation.searchFestival2, numOfRows, pageNo, arrange, null, areaCode, sigunguCode, null, null, null, null, formattedEventStartDate, formattedEventEndDate,null);
    }

    @Override
    public Mono<String> getContentBySearchStay2(Long numOfRows, Long pageNo, Arrange arrange, AreaCode areaCode, Long sigunguCode) {
        return getContent(Operation.searchStay2, numOfRows, pageNo, arrange, null,areaCode,sigunguCode,null,null,null,null,null,null,null);
    }

    @Override
    public Mono<String> getContentByDetailCommon2(Long numOfRows, Long pageNo, Long contentId) {
        return getContent(Operation.detailCommon2, numOfRows, pageNo,null,null,null,null,null,null,null,null,null,null,contentId);
    }

    @Override
    public Mono<String> getContentByDetailIntro2(Long numOfRows, Long pageNo, long contentId, ContentTypeId contentTypeId) {
        return getContent(Operation.detailIntro2, numOfRows, pageNo,null,contentTypeId,null,null,null,null,null,null,null,null,contentId);
    }

    @Override
    public Mono<String> getContentByDetailInfo2(Long numOfRows, Long pageNo, long contentId, ContentTypeId contentTypeId) {
        return getContent(Operation.detailInfo2, numOfRows, pageNo,null,contentTypeId,null,null,null,null,null,null,null,null,contentId);
    }

    @Override
    public Mono<String> getContentByDetailImage2(Long numOfRows, Long pageNo, Long contentId) {
        return getContent(Operation.detailImage2, numOfRows, pageNo, null, null, null, null, null, null, null, null, null, null, contentId);
    }

    @Override
    public Mono<String> getContentByAreaBasedSyncList2(Long numOfRows, Long pageNo, AreaCode areaCode, Long sigunguCode, Arrange arrange, ContentTypeId contentTypeId) {
        return getContent(Operation.areaBasedSyncList2, numOfRows, pageNo, arrange, contentTypeId, areaCode, sigunguCode, null, null, null, null, null, null,null);
    }

    @Override
    public Mono<String> getContentByDetailPetTour2(Long numOfRows, Long pageNo, Long contentId) {
        return getContent(Operation.detailPetTour2, numOfRows, pageNo, null, null, null, null, null, null, null, null, null, null, contentId);
    }

    @Override
    public Mono<String> getContentByLdongCode2() {
        return getContent(Operation.ldongCode2,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    @Override
    public Mono<String> getContentByLclsSystemCode2() {
        return getContent(Operation.lclsSystmCode2,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }

    private Mono<String> getContent(Operation operation, @Nullable Long numOfRows, @Nullable Long pageNo, @Nullable Arrange arrange,
                                    @Nullable ContentTypeId contentTypeId, @Nullable AreaCode areaCode, @Nullable Long sigunguCode,
                                    @Nullable Long radius, @Nullable Double mapX, @Nullable Double mapY, @Nullable String keyword,
                                    @Nullable String eventStartDate, @Nullable String eventEndDate, @Nullable Long contentId) {
        String url = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceURL();
        String key = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceEncodingKey();
        String mobileOs = MobileOS.WEB.name();
        String mobileAppName = appProperties.getKoreaTourOrganization().getMobileAppName();

        long safeNumOfRows = (numOfRows != null) ? numOfRows : 10L;
        long safePageNo = (pageNo != null) ? pageNo : 1L;

        URI uri = URIGenerator(url, operation.name(), key, mobileOs, mobileAppName, safeNumOfRows, safePageNo,
                arrange, contentTypeId, areaCode, sigunguCode,radius,mapX,mapY,keyword,eventStartDate,eventEndDate,contentId);

        return webClientService.request(uri);
    }

}
