package com.anynoti.web;

public enum AuthorizationType {
    BEARER;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}