package com.anynoti.oauith2.handler;

import com.anynoti.oauith2.provider.JwtTokenProvider;
import com.anynoti.domain.user.ProviderType;
import com.anynoti.oauith2.dto.TokenResDto;
import com.anynoti.domain.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(JwtTokenProvider jwtTokenProvider,
        ObjectMapper objectMapper, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res,
        Authentication authentication) throws IOException {
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String providerId = authentication.getName();
        ProviderType providerType = ProviderType
            .valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

        String jwtToken = jwtTokenProvider.createToken(providerId, providerType);

        log.info("onAuthenticationSuccess");

        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.OK.value());
        res.setCharacterEncoding("utf-8");
        res.getWriter()
            .write(objectMapper.writeValueAsString(
                TokenResDto.builder()
                        .access_token(jwtToken)
                        .provider_Id(providerId)
                        .build()));
    }

}