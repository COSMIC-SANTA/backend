package SANTA.backend.global.utils.api;

import SANTA.backend.global.utils.api.domain.TouristApiResponse;

public interface APIRequester {
    TouristApiResponse getContentByAreaCode2();

    TouristApiResponse getContentByCategoryCode2();

    public TouristApiResponse getContentByAreaBasedList2();

    TouristApiResponse getContentByLocationBasedList2();

    public TouristApiResponse getContentBySearchKeyword2();

    TouristApiResponse getContentBySearchFestival2();

    TouristApiResponse getContentBySearchStay2();

    TouristApiResponse getContentByDetailCommon2();

    TouristApiResponse getContentByDetailIntro2();

    TouristApiResponse getContentByDetailInfo2();

    TouristApiResponse getContentByDetailImage2();

    TouristApiResponse getContentByAreaBasedSyncList2();

    TouristApiResponse getContentByDetailPetTour2();

    TouristApiResponse getContentByLdongCode2();

    TouristApiResponse getContentByLclsSystemCode2();
}
