package com.lisoft.exagen.infrastructure.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class JwtManager {
    private  JwtManager() {}

    public static String getUserId(Authentication authentication) {
        Jwt jwt = getToken(authentication);

        return jwt.getClaim("userId").toString();
    }

    public static String getRole(Authentication authentication) {
        Jwt jwt = getToken(authentication);

        return jwt.getClaim("role").toString();
    }

    private static Jwt getToken(Authentication authentication) {
        JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;

        return jwtToken.getToken();
    }
}
