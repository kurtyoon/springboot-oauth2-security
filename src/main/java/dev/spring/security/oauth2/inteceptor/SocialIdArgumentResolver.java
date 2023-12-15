package dev.spring.security.oauth2.inteceptor;

import dev.spring.security.oauth2.annotation.SocialId;
import dev.spring.security.oauth2.constant.Constants;
import dev.spring.security.oauth2.exception.CustomException;
import dev.spring.security.oauth2.exception.ErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SocialIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(String.class)
                && parameter.hasParameterAnnotation(SocialId.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final Object socialId = webRequest.getAttribute(Constants.SOCIAL_ID_CLAIM_NAME, WebRequest.SCOPE_REQUEST);

        if (socialId == null) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return socialId.toString();
    }
}
