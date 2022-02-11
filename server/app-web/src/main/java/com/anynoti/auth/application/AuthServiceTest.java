package com.anynoti.auth.application;

import com.anynoti.common.JwtProperties;
import com.anynoti.enums.appweb.DevEnvironment;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceTest extends AuthService {

    public AuthServiceTest(JwtProperties jwtProperties) {
        super.setJwtTokenProvider(
            jwtProperties.getSecretKey(),
            jwtProperties.getExpireSeconds()
        );
    }

    @Override
    DevEnvironment getEnvironment() {
        return DevEnvironment.TEST;
    }

}