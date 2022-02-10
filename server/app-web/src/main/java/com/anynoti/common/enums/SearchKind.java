package com.anynoti.common.enums;

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

}