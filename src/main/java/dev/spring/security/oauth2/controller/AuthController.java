package dev.spring.security.oauth2.controller;

import dev.spring.security.oauth2.annotation.UserId;
import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.dto.auth.AuthSignUpDto;
import dev.spring.security.oauth2.dto.exception.ResponseDto;
import dev.spring.security.oauth2.dto.jwt.JwtTokenDto;
import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import dev.spring.security.oauth2.service.AuthService;
import dev.spring.security.oauth2.utility.CookieUtil;
import dev.spring.security.oauth2.utility.HeaderUtil;
import dev.spring.security.oauth2.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseDto<?> signUp(
            @RequestBody @Valid AuthSignUpDto authSignUpDto
            ) {
        authService.signUp(authSignUpDto);
        return ResponseDto.ok(null);
    }

    @PostMapping("/reissue")
    public ResponseDto<?> reissue(
            HttpServletRequest request,
            HttpServletResponse response,
            @UserId Long userId
    ) {
        String refreshToken = request.getHeader("User-Agent") !=
                null ? CookieUtil.refineCookie(request, "refresh_token")
                        .orElseThrow(() -> new CustomException(ErrorCode.MISSING_REQUEST_PARAMETER))
                : HeaderUtil.refineHeader(request, Constants.AUTHORIZATION, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CustomException(ErrorCode.MISSING_REQUEST_PARAMETER));

        JwtTokenDto jwtTokenDto = authService.reissue(userId, refreshToken);

        if (request.getHeader("User-Agent") != null) {
            CookieUtil.addSecureCookie(response, "refresh_token", jwtTokenDto.refreshToken(), jwtUtil.getRefreshTokenExpiration());
            jwtTokenDto = JwtTokenDto.of(jwtTokenDto.accessToken(), null);
        }
        return ResponseDto.ok(jwtTokenDto);
    }
}
