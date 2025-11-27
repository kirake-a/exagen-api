package com.lisoft.exagen.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SurveyStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String value;

    SurveyStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
