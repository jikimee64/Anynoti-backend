package com.anynoti.oauith2.attributes;

import com.anynoti.domain.user.ProviderType;
import java.util.Map;

public class Oauth2Factory {
    public static Oauth2Attributes getOAuth2Attributes(ProviderType providerType, Map<String, Object> attributes){
        switch (providerType) {
            case GOOGLE: return new GoogleOauth2Attributes(attributes);
            default: throw new IllegalArgumentException("Invalid OAuth Provider Type");
        }
    }
}