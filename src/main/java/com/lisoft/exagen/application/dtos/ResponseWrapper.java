package com.lisoft.exagen.application.dtos;

public record ResponseWrapper<T>(
        boolean success,
        String message,
        T data
) {}
