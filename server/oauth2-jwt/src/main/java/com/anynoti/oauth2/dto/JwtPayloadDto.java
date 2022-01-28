package com.anynoti.oauth2.dto;

import com.anynoti.domain.user.ProviderType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtPayloadDto {
    private String providerId;
    private ProviderType providerType;
}