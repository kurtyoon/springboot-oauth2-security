package dev.spring.security.oauth2.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum EUserType {
    GUEST("GUEST", "ROLE_GUEST"),
    USER("USER", "ROLE_USER"),
    AGENCY("AGENCY", "ROLE_AGENCY"),
    ADMIN("ADMIN", "ROLE_ADMIN");

    private final String name;
    private final String authority;

    public static EUserType fromName(String name) {
        return Arrays.stream(EUserType.values()).
                filter(userType -> userType.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No such user type: " + name));
    }
}
