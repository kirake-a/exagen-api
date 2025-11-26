package com.lisoft.exagen.domain.utils;

import java.util.Objects;

import static com.lisoft.exagen.domain.utils.Constants.USER_ID_CANNOT_BE_NULL_MESSAGE;

public class DataValidator {
    private DataValidator() {}

    public static void validateUserId(String userId) {
        if (Objects.isNull(userId) || userId.isEmpty()) {
            throw new  IllegalArgumentException(USER_ID_CANNOT_BE_NULL_MESSAGE);
        }
    }
}
