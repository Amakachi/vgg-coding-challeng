package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;


public class ForbiddenRequest extends AbstractException {
    public ForbiddenRequest(String code, String message){
        super(code,message);
    }

}
