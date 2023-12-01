package dev.spring.security.oauth2.inteceptor;

import dev.spring.security.oauth2.annotation.UserId;
import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(UserId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Object userIdObj = webRequest.getAttribute("userId", WebRequest.SCOPE_REQUEST);
        if (userIdObj == null) {
            throw new CustomException(ErrorCode.FORBIDDEN_ERROR);
        }
        return Long.valueOf(userIdObj.toString());
    }
}
