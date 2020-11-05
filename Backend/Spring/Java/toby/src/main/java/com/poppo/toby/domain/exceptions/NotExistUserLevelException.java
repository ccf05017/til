package com.poppo.toby.domain.exceptions;

public class NotExistUserLevelException extends RuntimeException {
    public NotExistUserLevelException(String message) {
        super(message);
    }
}
