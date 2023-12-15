package dev.spring.security.oauth2.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EmailVerifyDto(
        @JsonProperty("email")
        String email,

        @JsonProperty("code")
        String code
) {
}
