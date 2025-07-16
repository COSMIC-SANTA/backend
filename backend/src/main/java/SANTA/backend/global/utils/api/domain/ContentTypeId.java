package SANTA.backend.global.utils.api.domain;

public enum ContentTypeId {
    TOUR_PLACE(12),
    CULTURAL_FACILITY(14),
    FESTIVAL(15),
    TRAVEL_COURSE(25),
    LEISURE_SPORTS(28),
    STAY(32),
    SHOPPING(38),
    RESTAURANT(39);

    private final int contentTypeId;

    ContentTypeId(int contentTypeId){
        this.contentTypeId = contentTypeId;
    }

}
