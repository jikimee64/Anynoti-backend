package com.anynoti.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum ProviderType {
    GOOGLE("Google");

    private String provider;

}