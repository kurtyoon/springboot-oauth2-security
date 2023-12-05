package dev.spring.security.oauth2.domain;

import dev.spring.security.oauth2.type.ELoginProvider;
import dev.spring.security.oauth2.type.EUserType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq")
    private Long id;

    @Column(name = "social_id", unique = true)
    private String socialId;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private EUserType userType;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "is_login")
    private boolean isLogin;

    @Builder
    public User(String socialId, String password, EUserType userType, ELoginProvider provider) {
        this.socialId = socialId;
        this.password = password;
        this.userType = userType;
        this.provider = provider;
        this.createdAt = LocalDate.now();
        this.refreshToken = null;
        this.isLogin = false;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.isLogin = true;
    }

    public void logout() {
        this.refreshToken = null;
        this.isLogin = false;
    }
}
