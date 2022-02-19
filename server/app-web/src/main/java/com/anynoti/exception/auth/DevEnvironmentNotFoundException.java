package com.anynoti.exception.auth;

import com.anynoti.exception.ApplicationException;

public class DevEnvironmentNotFoundException extends ApplicationException {

    public DevEnvironmentNotFoundException() {
    }

    public DevEnvironmentNotFoundException(String message) {
        super(message);
    }

}