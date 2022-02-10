package com.anynoti.auth.application;

import com.anynoti.LoginUser;
import com.anynoti.oauth2.dto.JwtPayloadDto;
import com.anynoti.oauth2.provider.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private JwtTokenProvider jwtTokenProvider;

    public AuthService(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginUser findRequestUserByToken(HttpServletRequest request)
        throws JsonProcessingException {
        //TODO: enum? static final?
        String authentication = (String) request.getAttribute("Authentication");

        if(Objects.isNull(authentication)){
            //TODO: 예외처리
        }

        String jwtToken = jwtTokenProvider.extractToken(request);
        JwtPayloadDto jwtPayloadDto = jwtTokenProvider.extractJwtPayload(jwtToken);

        return LoginUser.builder()
            .providerId(jwtPayloadDto.getProviderId())
            .providerType(jwtPayloadDto.getProviderType())
            .build();
    }
}
