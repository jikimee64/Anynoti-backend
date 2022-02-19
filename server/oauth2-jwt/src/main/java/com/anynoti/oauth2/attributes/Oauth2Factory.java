package com.anynoti.oauth2.attributes;

import com.anynoti.enums.ProviderType;
import java.util.Map;

public class Oauth2Factory {
    public static Oauth2Attributes getOAuth2Attributes(ProviderType providerType, Map<String, Object> attributes){
        switch (providerType) {
            case GOOGLE: return new GoogleOauth2Attributes(attributes);
            default: throw new IllegalArgumentException("Invalid OAuth Provider Type");
        }
    }
}