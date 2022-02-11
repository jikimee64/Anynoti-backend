package com.anynoti.auth.application;

import com.anynoti.LoginUser;
import com.anynoti.enums.appweb.DevEnvironment;
import com.anynoti.exception.auth.InvalidTokenException;
import com.anynoti.jwt.JwtPayloadDto;
import com.anynoti.jwt.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public abstract class AuthService {

    private JwtTokenProvider jwtTokenProvider;

    public void setJwtTokenProvider(String secretKey, Long expireSeconds){
        this.jwtTokenProvider = new JwtTokenProvider(
            new ObjectMapper(),
            secretKey,
            expireSeconds
        );
    }

    public LoginUser findRequestUserByToken(HttpServletRequest request)
        throws JsonProcessingException {
        //TODO: enum? static final?
        String authentication = request.getHeader("Authorization");
        if(Objects.isNull(authentication)){
            //TODO: 예외처리, enum으로 대체
            throw new InvalidTokenException("토큰이 존재하지 않습니다.");
        }

        String jwtToken = jwtTokenProvider.extractToken(request);
        JwtPayloadDto jwtPayloadDto = jwtTokenProvider.extractJwtPayload(jwtToken);

        return LoginUser.builder()
            .providerId(jwtPayloadDto.getProviderId())
            .providerType(jwtPayloadDto.getProviderType())
            .build();
    }

    abstract DevEnvironment getEnvironment();

}
