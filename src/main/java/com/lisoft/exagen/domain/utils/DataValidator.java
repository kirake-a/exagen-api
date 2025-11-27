package com.lisoft.exagen.domain.utils;

import com.lisoft.exagen.domain.exceptions.InvalidArgumentException;
import com.lisoft.exagen.domain.exceptions.UnauthorizedAccessException;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.lisoft.exagen.domain.utils.Constants.USER_ID_CANNOT_BE_NULL_MESSAGE;

public class DataValidator {
    private DataValidator() {}

    public static void validateUserId(String userId) {
        if (Objects.isNull(userId) || userId.isEmpty()) {
            throw new  IllegalArgumentException(USER_ID_CANNOT_BE_NULL_MESSAGE);
        }
    }

    public static void validateQuestions2Add(List openQuestions, List closedQuestions) {
        if (openQuestions.isEmpty() && closedQuestions.isEmpty()) {
            throw new IllegalArgumentException("There are no questions to add");
        }
    }

    public static void validateOwnership(String currentUserId, String resourceOwnerId) {
        if (Objects.isNull(currentUserId) || currentUserId.isEmpty()) {
            throw new InvalidArgumentException("Cannot validate owner with null or empty ownerId");
        }
        if (Objects.isNull(resourceOwnerId) || resourceOwnerId.isEmpty()) {
            throw new InvalidArgumentException("Cannot validate owner with null or empty resourceId");
        }
        if (!currentUserId.equals(resourceOwnerId)) {
            throw new UnauthorizedAccessException("You do not look like the resource owner");
        }
    }
}
