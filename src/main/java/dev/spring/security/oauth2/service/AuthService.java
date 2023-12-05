package dev.spring.security.oauth2.service;

import dev.spring.security.oauth2.domain.User;
import dev.spring.security.oauth2.dto.auth.AuthSignUpDto;
import dev.spring.security.oauth2.dto.jwt.JwtTokenDto;
import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import dev.spring.security.oauth2.repository.UserRepository;
import dev.spring.security.oauth2.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(AuthSignUpDto authSignUpDto) {
        userRepository.save(
                User.signUp(
                        authSignUpDto,
                        bCryptPasswordEncoder.encode(authSignUpDto.password())
                )
        );
    }

    @Transactional
    public JwtTokenDto reissue(Long userId, String refreshToken) {
        User user = userRepository.findByIdAndRefreshTokenAndIsLogin(userId, refreshToken, true)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ERROR));
        JwtTokenDto jwtTokenDto = jwtUtil.generateTokens(user.getId(), user.getUserType());
        user.updateRefreshToken(jwtTokenDto.refreshToken());

        return jwtTokenDto;
    }
}
