package dev.spring.security.oauth2.security;

import dev.spring.security.oauth2.repository.UserRepository;
import dev.spring.security.oauth2.type.EUserType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
    @Getter private final Long id;
    @Getter private final EUserType userType;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public static CustomUserDetails create(UserRepository.UserSecurityForm form) {
        return CustomUserDetails.builder()
                .id(form.getId())
                .userType(form.getUserType())
                .password(form.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(form.getUserType().getAuthority())))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
