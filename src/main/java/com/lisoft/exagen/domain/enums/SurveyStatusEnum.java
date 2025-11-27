package com.lisoft.exagen.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SurveyStatusEnum {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    SurveyStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static SurveyStatusEnum fromValue(String text) {
        if (text == null) return INACTIVE;

        for (SurveyStatusEnum status : SurveyStatusEnum.values()) {
            if (status.value.equalsIgnoreCase(text)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Unknown status: " + text);
    }
}
