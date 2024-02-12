package com.payhere.shop.util.exception;

public class ProductNotFountException extends RuntimeException {
    private int errorCode;

    public ProductNotFountException() {
        super(ExceptionMessage.PRODUCT_NOT_FOUND.get());
        this.errorCode = 400;
    }
}
