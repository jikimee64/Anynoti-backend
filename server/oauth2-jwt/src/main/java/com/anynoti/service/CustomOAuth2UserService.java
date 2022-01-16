package com.anynoti.service;

import com.anynoti.attributes.Oauth2Attributes;
import com.anynoti.attributes.Oauth2Factory;
import com.anynoti.attributes.UserAttributes;
import com.anynoti.user.ProviderType;
import com.anynoti.user.User;
import com.anynoti.user.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        Oauth2Attributes oAuth2Attributes = Oauth2Factory.getOAuth2Attributes(providerType, oAuth2User.getAttributes());
        String providerId = oAuth2Attributes.getId();

        //TODO: 권한 계층 설계 후 수정
       // Iterator<? extends GrantedAuthority> iterator = (oAuth2User.getAuthorities()).iterator();
        //String role = iterator.next().getAuthority();

        User user = userRepository.findUserByProviderId(providerId).orElseGet(() -> userRepository.save(getUserEntity(providerId, providerType)));

        return UserAttributes.create(user, oAuth2User.getAttributes());
    }

    private User getUserEntity(String providerId, ProviderType providerType){
        return User.builder()
            .providerId(providerId)
            .providerType(providerType)
            .build();
    }

}