package com.payhere.shop.util.exception;

public class NotAllowedManagerException extends RuntimeException{
    private int errorCode;

    public NotAllowedManagerException() {
        super(ExceptionMessage.NOT_ALLOWED_MANAGER.get());
        this.errorCode = 403;
    }
}
