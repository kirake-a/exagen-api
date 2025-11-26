package com.lisoft.exagen.domain.models;

import java.util.Set;

public record TestCategory(
        Integer id,
        String name,
        String userId,
        Set<String> tests
) {}
