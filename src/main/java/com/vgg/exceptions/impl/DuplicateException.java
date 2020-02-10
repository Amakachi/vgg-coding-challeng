package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;



public class DuplicateException extends AbstractException {

    public DuplicateException(String code, String message){
        super(code,message);
    }

}
