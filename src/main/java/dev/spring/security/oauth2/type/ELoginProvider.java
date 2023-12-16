package dev.spring.security.oauth2.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ELoginProvider {
    DEFAULT("DEFAULT"),
    GOOGLE("GOOGLE");

    private final String name;

    @Override
    public String toString() {
        return this.name;
    }
}
