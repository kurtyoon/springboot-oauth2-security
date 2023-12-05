package dev.spring.security.oauth2.constant;

import java.util.List;

public class Constants {
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_TYPE_CLAIM_NAME = "utp";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION = "Authorization";

    public static List<String> NO_AUTH_WHITE_LABEL = List.of(
            "/swagger-ui/index.html",
            "/swagger-ui/swagger-ui.css",
            "/swagger-ui/swagger-initializer.js",
            "/swagger-ui/swagger-ui-standalone-preset.js",
            "/swagger-ui/index.css",
            "/swagger-ui/swagger-ui-bundle.js",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs/public-api",
            "/swagger-ui/favicon-32x32.png",
            "/api/v1/swagger-ui/index.html",
            "/guest/**",
            "/auth/sign-up",
            "/auth/sign-in",
            "/REDIRECT_URL"
    );
}
