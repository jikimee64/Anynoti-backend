package com.anynoti.auth.interceptor;

import com.anynoti.auth.application.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptors implements HandlerInterceptor {

    AuthService authService;

    public AuthInterceptors(AuthService authService) {
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
