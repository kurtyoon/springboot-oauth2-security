package dev.spring.security.oauth2.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverOAuth2Util {
    /**
     * Naver OAuth2
     */
    private String NAVER_AUTHENTICATION_URL;
    private String NAVER_TOKEN_URL;
    private String NAVER_USER_INFO_URL;
    private String NAVER_CLIENT_ID;
    private String NAVER_CLIENT_SECRET;
    private String NAVER_REDIRECT_URI;
    private String NAVER_SCOPE;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getNaverRedirectUrl() {
        return NAVER_AUTHENTICATION_URL
                + "?client_id=" + NAVER_CLIENT_ID
                + "&redirect_uri=" + NAVER_REDIRECT_URI
                + "&response_type=code"
                + "&state=STATE_STRING";
    }


}
