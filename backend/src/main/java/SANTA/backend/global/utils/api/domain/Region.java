package SANTA.backend.global.utils.api.domain;

import lombok.Getter;

@Getter
public enum Region {

    SEOUL("1", "서울"),
    INCHEON("2", "인천"),
    DAEJEON("3", "대전"),
    DAEGU("4", "대구"),
    GWANGJU("5", "광주"),
    BUSAN("6", "부산"),
    ULSAN("7", "울산"),
    SEJONG("8", "세종특별자치시"),
    GYEONGGI("31", "경기도"),
    GANGWON("32", "강원특별자치도"),
    CHUNGCHEONGBUK("33", "충청북도"),
    CHUNGCHEONGNAM("34", "충청남도"),
    GYEONGSANGBUK("35", "경상북도"),
    GYEONGSANGNAM("36", "경상남도"),
    JEONBUK("37", "전북특별자치도"),
    JEONNAM("38", "전라남도"),
    JEJU("39", "제주도");

    private final String code;
    private final String name;

    Region(String code, String name) {
        this.code = code;
        this.name = name;
    }

}
