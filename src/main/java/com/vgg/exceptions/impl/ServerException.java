package com.vgg.exceptions.impl;


import com.vgg.exceptions.AbstractException;


public class ServerException extends AbstractException {
    public ServerException(String code, String message){
        super(code,message);
    }


}
