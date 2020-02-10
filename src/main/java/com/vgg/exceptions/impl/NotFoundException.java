package com.vgg.exceptions.impl;

import com.vgg.exceptions.AbstractException;


/**
 * Created by Obinna.Onwuakagba on 7/6/2019.
 */
public class NotFoundException extends AbstractException {
    public NotFoundException(String code, String message){
        super(code,message);
    }


}
