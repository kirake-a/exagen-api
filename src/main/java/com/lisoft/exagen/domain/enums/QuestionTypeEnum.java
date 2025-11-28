package com.lisoft.exagen.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum QuestionTypeEnum {
    OPEN("OPEN"),
    CLOSED("CLOSED");

    private String value;

    QuestionTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
