package SANTA.backend.global.utils.api;

import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.utils.api.domain.MobileOS;
import SANTA.backend.global.utils.api.domain.Operation;
import SANTA.backend.global.utils.api.domain.TouristApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KoreanTourInfoServiceResponder implements APIRequester {

    private final AppProperties appProperties;
    private final WebClientService webClientService;


    private TouristApiResponse getContent(Operation operation){
        String url = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceURL();
        String key = appProperties.getKoreaTourOrganization().getKoreaTourInfoServiceDecodingKey();
        String mobileOs = MobileOS.WEB.name();
        String mobileAppName = appProperties.getKoreaTourOrganization().getMobileAppName();

        return webClientService.request(url,operation.name(), key, mobileOs, mobileAppName);
    }

    @Override
    public TouristApiResponse getContentByAreaCode2() {
        return getContent(Operation.areaCode2);
    }

    @Override
    public TouristApiResponse getContentByCategoryCode2() {
        return getContent(Operation.categoryCode2);
    }

    @Override
    public TouristApiResponse getContentByAreaBasedList2() {
        return getContent(Operation.areaBasedList2);
    }

    @Override
    public TouristApiResponse getContentByLocationBasedList2(){
        return getContent(Operation.locationBasedList2);
    }

    @Override
    public TouristApiResponse getContentBySearchKeyword2(){
        return getContent(Operation.searchKeyword2);
    }

    @Override
    public TouristApiResponse getContentBySearchFestival2(){
        return getContent(Operation.searchFestival2);
    }

    @Override
    public TouristApiResponse getContentBySearchStay2(){
        return getContent(Operation.searchStay2);
    }

    @Override
    public TouristApiResponse getContentByDetailCommon2(){
        return getContent(Operation.detailCommon2);
    }

    @Override
    public TouristApiResponse getContentByDetailIntro2(){
        return getContent(Operation.detailIntro2);
    }

    @Override
    public TouristApiResponse getContentByDetailInfo2(){
        return getContent(Operation.detailInfo2);
    }

    @Override
    public TouristApiResponse getContentByDetailImage2(){
        return getContent(Operation.detailImage2);
    }

    @Override
    public TouristApiResponse getContentByAreaBasedSyncList2(){
        return getContent(Operation.areaBasedSyncList2);
    }

    @Override
    public TouristApiResponse getContentByDetailPetTour2(){
        return getContent(Operation.detailPetTour2);
    }

    @Override
    public TouristApiResponse getContentByLdongCode2(){
        return getContent(Operation.ldongCode2);
    }

    @Override
    public TouristApiResponse getContentByLclsSystemCode2(){
        return getContent(Operation.lclsSystmCode2);
    }

}
