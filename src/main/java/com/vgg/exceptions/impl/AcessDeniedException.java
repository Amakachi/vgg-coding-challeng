package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;



public class AcessDeniedException extends AbstractException {

    public AcessDeniedException(String code, String message){
        super(code,message);
    }

}
