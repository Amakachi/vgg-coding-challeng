package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;


public class BadGatewayException extends AbstractException {
    public BadGatewayException(String code, String message){
        super(code,message);
    }

}
