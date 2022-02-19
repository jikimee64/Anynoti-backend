package com.anynoti.jwt;

import com.anynoti.enums.ProviderType;
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