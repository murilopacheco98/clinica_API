package com.growdev.ecommerce.exceptions.exception;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String msg) {
        super(msg);
    }
}
