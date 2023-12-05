package dev.spring.security.oauth2.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ELoginProvider {
    DEFAULT("DEFAULT"),
    GOOGLE("GOOGLE"),
    NAVER("NAVER"),
    KAKAO("KAKAO"),
    FACEBOOK("FACEBOOK"),
    TWITTER("TWITTER"),
    GITHUB("GITHUB"),
    LINE("LINE"),
    APPLE("APPLE");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
