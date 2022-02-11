package com.anynoti.auth.application;

import com.anynoti.enums.appweb.DevEnvironment;
import com.anynoti.exception.auth.DevEnvironmentNotFoundException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceFactory {

    private final List<AuthService> oauthManagers;

    public AuthServiceFactory(List<AuthService> oauthManagers) {
        this.oauthManagers = oauthManagers;
    }

    public AuthService findAuthServiceEnvironment(DevEnvironment environment){
        return oauthManagers.stream()
            .filter(oauthManager -> oauthManager.getEnvironment() == environment)
            .findFirst()
            .orElseThrow(DevEnvironmentNotFoundException::new);
    }

}
