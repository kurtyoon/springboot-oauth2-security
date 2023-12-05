package dev.spring.security.oauth2.constant;

import java.util.List;

public class Constants {
    public static String USER_ID_CLAIM_NAME = "uid";
    public static String USER_TYPE_CLAIM_NAME = "utp";
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION = "Authorization";

    public static List<String> NO_AUTH_WHITE_LABEL = List.of(
            "/**"
    );
}
