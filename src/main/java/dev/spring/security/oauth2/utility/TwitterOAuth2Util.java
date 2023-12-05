package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TwitterOAuth2Util {
    /**
     * Twitter OAuth2
     */
    private String TWITTER_AUTHENTICATION_URL;
    private String TWITTER_TOKEN_URL;
    private String TWITTER_USER_INFO_URL;
    private String TWITTER_CLIENT_ID;
    private String TWITTER_CLIENT_SECRET;
    private String TWITTER_REDIRECT_URI;
}
