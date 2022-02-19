package com.anynoti.oauth2.provider;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

public enum Oauth2CustomProvider {
    GOOGLE {
        public ClientRegistration.Builder getBuilder(String registrationId) {
            ClientRegistration.Builder builder = getBuilder(registrationId,
                ClientAuthenticationMethod.CLIENT_SECRET_POST, DEFAULT_LOGIN_REDIRECT_URL);
            builder.scope("openid", "profile", "email");
            builder.authorizationUri(AUTHORIZATION_URI);
            builder.tokenUri(TOKEN_URI);
            builder.userInfoUri(USER_INFO_URI);
            builder.userNameAttributeName(IdTokenClaimNames.SUB);
            builder.clientName("Google");
            builder.jwkSetUri(JWK_URI);
            return builder;
        }
    };

    //TODO: 도메인 구매후 수정
    private static final String BASE_URL = "http://localhost:8080";
    private static final String DEFAULT_LOGIN_REDIRECT_URL = BASE_URL + "/login/oauth2/code/google";
    private static final String TOKEN_URI = "https://www.googleapis.com/oauth2/v4/token";
    private static final String USER_INFO_URI = "https://www.googleapis.com/oauth2/v3/userinfo";
    private static final String AUTHORIZATION_URI = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String JWK_URI = "https://www.googleapis.com/oauth2/v3/certs";

    protected final ClientRegistration.Builder getBuilder(String registrationId,
        ClientAuthenticationMethod method, String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }

    public abstract ClientRegistration.Builder getBuilder(String registrationId);
}