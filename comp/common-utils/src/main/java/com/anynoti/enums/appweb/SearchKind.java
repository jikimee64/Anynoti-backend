package com.anynoti.enums.appweb;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CodeToEnumConverterFactory custom 하여 kind 값으로 파라미터 전달
 */
@Getter
@AllArgsConstructor
public enum SearchKind implements Constant{
    TODO("todo"),
    NOTI("noti"),
    LIKE("like");

    private final String kind;

    public static SearchKind findSearchKind(SearchKind param){
        return Arrays.stream(SearchKind.values())
            .filter(searchKind -> searchKind == param)
            .findAny()
            .orElse(SearchKind.TODO);
    }

}