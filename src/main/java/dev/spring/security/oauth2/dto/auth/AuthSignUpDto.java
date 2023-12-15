package dev.spring.security.oauth2.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthSignUpDto (
        @JsonProperty("social_id")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*_).{4,20}$",
                message = "아이디는 4자 이상 20자 이하로 입력해주세요."
        )
        @NotNull(message = "아이디는 필수 입력값입니다.")
        String socialId,

        @JsonProperty("password")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%]).{8,16}$",
                message = "비밀번호는 대문자 1개 이상, 소문자 1개 이상, 숫자 1개 이상, 특수문자(!, @, #, %, $) 1개 이상으로 구성된 8~16자리 비밀번호로 입력해주세요."
        )
        @NotNull(message = "password는 필수 입력값입니다.")
        String password,

        @JsonProperty("name")
        @NotNull(message = "name은 필수 입력값입니다.")
        String name,

        @JsonProperty("email")
        @NotNull(message = "email은 필수 입력값입니다.")
        String email,

        @JsonProperty("agency")
        @NotNull(message = "agency는 필수 입력값입니다.")
        String agency
) {
}
