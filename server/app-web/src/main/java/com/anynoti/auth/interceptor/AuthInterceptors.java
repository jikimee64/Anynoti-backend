package com.anynoti.auth.interceptor;

import com.anynoti.auth.application.AuthServiceProduction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptors implements HandlerInterceptor {

    AuthServiceProduction authService;

    public AuthInterceptors(AuthServiceProduction authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

//        String token = AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
//
//        if(!authService.validateToken()){
//            return new InvalidTokenException();
//        }
//
//        request.setAttribute();

        return true;
    }
}
