package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FacebookOAuth2Util {
    /**
     * Facebook OAuth2
     */
    private String FACEBOOK_AUTHENTICATION_URL;
    private String FACEBOOK_TOKEN_URL;
    private String FACEBOOK_USER_INFO_URL;
    private String FACEBOOK_CLIENT_ID;
    private String FACEBOOK_CLIENT_SECRET;
    private String FACEBOOK_REDIRECT_URI;
}
