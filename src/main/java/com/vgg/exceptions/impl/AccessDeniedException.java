package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;



public class AccessDeniedException extends AbstractException {

    public AccessDeniedException(String code, String message){
        super(code,message);
    }

}
