package com.vgg.exceptions.impl;


public class RestCallException extends RuntimeException {

    private static final long serialVersionUID = -5919447122348400126L;
    private String code = "10500";

    public RestCallException() {
    }

    public RestCallException(String message) {
        super(message);
    }

    public RestCallException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
