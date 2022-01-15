package com.anynoti;

import com.anynoti.user.ProviderType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtPayloadDto {
    private String providerId;
    private ProviderType providerType;
}