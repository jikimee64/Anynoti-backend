package com.anynoti.oauth2.dto;

import com.anynoti.domain.user.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtPayloadDto {
    private String providerId;
    private ProviderType providerType;
}