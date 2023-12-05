package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleOAuth2Util {
    /**
     * Apple OAuth2
     */
    private String APPLE_AUTHENTICATION_URL;
    private String APPLE_TOKEN_URL;
    private String APPLE_USER_INFO_URL;
    private String APPLE_CLIENT_ID;
    private String APPLE_CLIENT_SECRET;
    private String APPLE_REDIRECT_URI;
}
