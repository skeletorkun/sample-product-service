package com.company.marketplace.exception;

public class ProductNotFoundException extends IllegalArgumentException {

    public ProductNotFoundException(String msg) {
        super(msg);
    }

    public ProductNotFoundException(String msg, Throwable err) {
        super(msg, err);
    }
}
