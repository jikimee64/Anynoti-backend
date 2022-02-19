//package com.anynoti.oauth2.filter;
//
//import com.anynoti.oauth2.provider.JwtTokenProvider;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import java.io.IOException;
//import java.util.Optional;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * TODO: 인터셉터로 옮길까 고민중
// */
//@Slf4j
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    public JwtTokenFilter(
//        JwtTokenProvider jwtTokenProvider) {
//        this.jwtTokenProvider = jwtTokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//        FilterChain filterChain) throws ServletException, IOException {
//        String jwtToken = jwtTokenProvider.extractToken(request);
//        try {
//            if (StringUtils.hasText(jwtToken) && (jwtTokenProvider.isValidToken(jwtToken))) {
//                setSecurityContext(jwtToken);
//            }
//            filterChain.doFilter(request, response);
//        } catch (AuthenticationException e) {
//            log.error(e.getMessage(), e);
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//        }
//    }
//
//    private void setSecurityContext(String jwtToken) throws JsonProcessingException {
//        if (nonExistedAuthenticated()) {
//            SecurityContext context = SecurityContextHolder.createEmptyContext();
//            Authentication authentication = jwtTokenProvider.extractAuthentication(jwtToken);
//            context.setAuthentication(authentication);
//
//            SecurityContextHolder.setContext(context);
//        }
//    }
//
//    private boolean nonExistedAuthenticated() {
//        log.info(
//            "SecurityContextHolder.getContext().getAuthentication() : {}",
//            SecurityContextHolder.getContext().getAuthentication());
//        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
//            .isEmpty();
//    }
//
//}