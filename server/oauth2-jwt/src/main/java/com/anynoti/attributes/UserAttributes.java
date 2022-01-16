package com.anynoti.attributes;

import com.anynoti.user.ProviderType;
import com.anynoti.user.User;
import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserAttributes implements OAuth2User, UserDetails {
    private String providerId;
    private ProviderType providerType;

    private Map<String, Object> attributes;

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Builder
    public UserAttributes(ProviderType providerType, String providerId) {
        this.providerType = providerType;
        this.providerId = providerId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    public static UserAttributes create(User user) {
        return UserAttributes.builder()
            .providerId(user.getProviderId())
            .providerType(user.getProviderType())
            .build();
    }

    public static UserAttributes create(User user,
        Map<String, Object> attributes){
        UserAttributes userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }

}