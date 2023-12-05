package dev.spring.security.oauth2.dto.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record JwtTokenDto (
        @JsonProperty("access_token")
        @NotNull(message = "access_token은 필수 입력 값입니다.")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken
) implements Serializable {
    public static JwtTokenDto of(String accessToken, String refreshToken) {
        return JwtTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
