package dev.spring.security.oauth2.inteceptor;

import dev.spring.security.oauth2.constant.Constants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class SocialIdInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        request.setAttribute(Constants.SOCIAL_ID_CLAIM_NAME, authentication.getName());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
