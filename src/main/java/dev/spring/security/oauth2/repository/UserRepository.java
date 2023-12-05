package dev.spring.security.oauth2.repository;

import dev.spring.security.oauth2.domain.User;
import dev.spring.security.oauth2.type.EUserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndRefreshTokenAndIsLogin(Long id, String refreshToken, Boolean isLogin);

    @Query("select u.id as id, u.userType as userType, u.password as password from User u where u.socialId = :socialId")
    Optional<UserSecurityForm> findSecurityFormBySocialId(String socialId);
    interface UserSecurityForm {
        Long getId();
        EUserType getUserType();
        String getPassword();
    }
}