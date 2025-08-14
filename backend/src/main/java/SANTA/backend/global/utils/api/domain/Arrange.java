package SANTA.backend.global.utils.api.domain;

import lombok.Getter;

@Getter
public enum Arrange {
    A("제목 순"),
    C("수정일 순"),
    D("생성일 순"),
    O("대표 이미지가 반드시 있을 때 제목 순"),
    Q("대표 이미지가 반드시 있을 때 수정일 순"),
    R("대표 이미지가 반드시 있을 때 생성일 순");

    private final String description;

    Arrange(String description){
        this.description = description;
    }
}
