package dev.spring.security.oauth2.utility;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
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
public class GoogleOAuth2Util {
    /**
     * Google OAuth2
     */
    @Value("${security.oauth2.google.authentication_url}")
    private String GOOGLE_AUTHENTICATION_URL;
    @Value("${security.oauth2.google.token_url}")
    private String GOOGLE_TOKEN_URL;
    @Value("${security.oauth2.google.user_info_url}")
    private String GOOGLE_USER_INFO_URL;
    @Value("${security.oauth2.google.client_id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${security.oauth2.google.client_secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${security.oauth2.google.redirect_uri}")
    private String GOOGLE_REDIRECT_URI;
    private final RestTemplate restTemplate = new RestTemplate();

    public String getGoogleRedirectUrl() {
        return GOOGLE_AUTHENTICATION_URL
                + "?client_id=" + GOOGLE_CLIENT_ID
                + "&redirect_uri=" + GOOGLE_REDIRECT_URI
                + "&response_type=code&scope=https://www.googleapis.com/auth/userinfo.profile";
    }

    public String getGoogleAccessToken(String code) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new org.springframework.util.LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", GOOGLE_CLIENT_ID);
        params.add("client_secret", GOOGLE_CLIENT_SECRET);
        params.add("redirect_uri", GOOGLE_REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(params, httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_TOKEN_URL,
                org.springframework.http.HttpMethod.POST,
                googleTokenRequest,
                String.class
        );

        return JsonParser.parseString(response.getBody()).getAsJsonObject().get("access_token").getAsString();
    }

    public String getGoogleUserInfo(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + accessToken);
        httpHeaders.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> googleUserInfoRequest = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(
                GOOGLE_USER_INFO_URL,
                org.springframework.http.HttpMethod.GET,
                googleUserInfoRequest,
                String.class
        );

        JsonElement element = JsonParser.parseString(response.getBody());
        return element.getAsJsonObject().get("id").getAsString();
    }
}
