package com.anynoti.enums.appweb;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DevEnvironment {
    LOCAL("local"),
    TEST("test");

    private String environment;

    public static DevEnvironment findDevEnvironment(String param){
        return Arrays.stream(DevEnvironment.values())
            .filter(environment -> environment.getEnvironment().equals(param))
            .findFirst()
            .orElse(null);
    }

}