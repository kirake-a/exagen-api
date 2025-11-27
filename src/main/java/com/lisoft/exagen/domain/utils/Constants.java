package com.lisoft.exagen.domain.utils;

public class Constants {
    private Constants() {}

    public static final String API_VERSION = "/api/v1";
    public static final String OPEN_API_VERSION = "v0.0.1";

    public static final String UNEXPECTED_ERROR_MESSAGE = "An unexpected error occurred: ";
    public static final String INVALID_ARGUMENT_MESSAGE = "Invalid argument: ";

    public static final String USER_ID_CANNOT_BE_NULL_MESSAGE = "User ID cannot be null";
    public static final String TEST_ID_CANNOT_BE_NULL_MESSAGE = "Test ID cannot be null";
    public static final String UNAUTHORIZED_ACCESS_TO_TEST = "You do not have access to this test";
    public static final String TEST_NOT_FOUND_MESSAGE = "The test your looking for was not found";

    public static final String QUESTION_NOT_FOUND_MESSAGE = "Question not found";
    public static final String UNAUTHORIZED_ACCESS_2_QUESTION = "Maybe your not authorized to access this questions";
    public static final String QUESTION_FOUND_SUCCESSFULLY = "Your question was found successfully";
    public static final String OPEN_QUESTIONS_NOT_FOUND_MESSAGE = "One or more open questions does not exist";
    public static final String CLOSED_QUESTIONS_NOT_FOUND_MESSAGE = "One or more closed questions does not exist";
    public static final String CANNOT_CREATE_EXAM_WITH_NO_QUESTIONS = "Look like you do not gave any questions";

    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found";
    public static final String UNAUTHORIZED_ACCESS_2_CATEGORY = "Maybe your not authorized to access this category";
}
