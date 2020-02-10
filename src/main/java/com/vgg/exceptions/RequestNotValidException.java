package com.vgg.exceptions;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;


public class RequestNotValidException extends RuntimeException {

    private final BindingResult bindingResult;

    public RequestNotValidException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("Validation failed for request object '")
                .append(this.bindingResult.getObjectName())
                .append("', with ").append(this.bindingResult.getErrorCount()).append(" error(s): ");
        for (ObjectError error : this.bindingResult.getAllErrors()) {
            sb.append("[").append(error).append("] ");
        }
        return sb.toString();
    }
}
