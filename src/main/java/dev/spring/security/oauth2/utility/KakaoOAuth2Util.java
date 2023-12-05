package dev.spring.security.oauth2.utility;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2Util {
    /**
     * Kakao OAuth2
     */
    @Value("${security.oauth2.kakao.authentication_url}")
    private String KAKAO_AUTHENTICATION_URL;
    @Value("${security.oauth2.kakao.token_url}")
    private String KAKAO_TOKEN_URL;
    @Value("${security.oauth2.kakao.user_info_url}")
    private String KAKAO_USER_INFO_URL;
    @Value("${security.oauth2.kakao.client_id}")
    private String KAKAO_CLIENT_ID;
    @Value("${security.oauth2.kakao.client_secret}")
    private String KAKAO_CLIENT_SECRET;
    @Value("${security.oauth2.kakao.redirect_uri}")
    private String KAKAO_REDIRECT_URI;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getKakaoRedirectUrl() {
        return KAKAO_AUTHENTICATION_URL
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URI
                + "&response_type=code";
    }

    public String getKakaoAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new org.springframework.util.LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("client_secret", KAKAO_CLIENT_SECRET);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_TOKEN_URL,
                org.springframework.http.HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        return JsonParser.parseString(response.getBody()).getAsJsonObject().get("access_token").getAsString();
    }

    public String getKakaoUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + accessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_USER_INFO_URL,
                org.springframework.http.HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        return element.getAsJsonObject().get("id").getAsString();
    }
}
