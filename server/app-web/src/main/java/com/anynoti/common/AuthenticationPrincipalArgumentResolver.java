package com.anynoti.common;

import com.anynoti.AuthenticationPrincipal;
import com.anynoti.LoginUser;
import com.anynoti.auth.application.AuthService;
import com.anynoti.auth.application.AuthServiceFactory;
import com.anynoti.enums.appweb.DevEnvironment;
import com.fasterxml.jackson.core.JsonProcessingException;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Value("${spring.profiles.active}")
    private String environment;

    private AuthService authService;
    private final JwtProperties jwtProperties;
    private final AuthServiceFactory authServiceFactory;

    public AuthenticationPrincipalArgumentResolver(
        JwtProperties jwtProperties,
        AuthServiceFactory authServiceFactory
    ) {
        this.jwtProperties = jwtProperties;
        this.authServiceFactory = authServiceFactory;
    }

    @PostConstruct
    public void postConstruct(){
        authService = authServiceFactory.findAuthServiceEnvironment(
            DevEnvironment.findDevEnvironment(environment)
        );
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAuthenticationPrincipal = parameter.hasParameterAnnotation(
            AuthenticationPrincipal.class);
        boolean hasLoginUserType = LoginUser.class.isAssignableFrom(parameter.getParameterType());

        return hasAuthenticationPrincipal && hasLoginUserType;
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory
    ) throws JsonProcessingException {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        return authService.findRequestUserByToken(request);
    }

}