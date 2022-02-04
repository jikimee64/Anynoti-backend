package com.anynoti.oauith2.web;

public enum AuthorizationType {
    BEARER;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}