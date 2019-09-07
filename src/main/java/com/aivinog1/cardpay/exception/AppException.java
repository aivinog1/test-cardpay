package com.aivinog1.cardpay.exception;

public abstract class AppException extends RuntimeException {

    protected AppException(String message) {
        super(message);
    }
}
