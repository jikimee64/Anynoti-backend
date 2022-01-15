package com.anynoti.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProviderType {
    GOOGLE("google");

    private String provider;

}