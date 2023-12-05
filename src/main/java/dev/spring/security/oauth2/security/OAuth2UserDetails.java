package dev.spring.security.oauth2.security;

import dev.spring.security.oauth2.type.EUserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class OAuth2UserDetails extends DefaultOAuth2User {
    private final Long id;
    private final EUserType userType;
    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public OAuth2UserDetails(Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attributes,
                             String nameAttributeKey, Long id, EUserType userType) {
        super(authorities, attributes, nameAttributeKey);
        this.id = id;
        this.userType = userType;
    }
}
