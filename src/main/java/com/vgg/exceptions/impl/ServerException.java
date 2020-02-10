package com.vgg.exceptions.impl;


import com.vgg.exceptions.AbstractException;


/**
 * Created by Obinna.Onwuakagba on 7/6/2019.
 */
public class ServerException extends AbstractException {
    public ServerException(String code, String message){
        super(code,message);
    }


}
