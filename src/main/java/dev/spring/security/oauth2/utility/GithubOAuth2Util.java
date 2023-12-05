package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubOAuth2Util {
    /**
     * Github OAuth2
     */
    private String GITHUB_AUTHENTICATION_URL;
    private String GITHUB_TOKEN_URL;
    private String GITHUB_USER_INFO_URL;
    private String GITHUB_CLIENT_ID;
    private String GITHUB_CLIENT_SECRET;
    private String GITHUB_REDIRECT_URI;
}
