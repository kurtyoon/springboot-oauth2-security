package dev.spring.security.oauth2.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ELoginProvider {
    KAKAO, GOOGLE
}
