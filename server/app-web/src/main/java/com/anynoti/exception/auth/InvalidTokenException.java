package com.anynoti.exception.auth;

import com.anynoti.exception.ApplicationException;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}