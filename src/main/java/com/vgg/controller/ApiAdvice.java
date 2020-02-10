package com.vgg.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.vgg.exceptions.RequestNotValidException;
import com.vgg.exceptions.impl.AccessDeniedException;
import com.vgg.exceptions.impl.BadRequestException;
import com.vgg.exceptions.impl.DuplicateException;
import com.vgg.exceptions.impl.NotFoundException;
import com.vgg.model.ApiResponse;
import com.vgg.model.Error;
import com.vgg.exceptions.InvalidEnumException;
import com.vgg.utils.EnumUtil;
import com.vgg.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@ControllerAdvice(annotations = {RestController.class})
@Order(Ordered.HIGHEST_PRECEDENCE)
@ResponseBody
public class ApiAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        Throwable cause = e.getMostSpecificCause();
        List<Error> errors = new ArrayList<>();
        if (cause instanceof InvalidFormatException ||
                cause instanceof InvalidEnumException) {
            Throwable t = ExceptionUtil.get(e, JsonMappingException.class);
            if (t != null) {
                List<JsonMappingException.Reference> reference = ((JsonMappingException) t).getPath();
                StringBuilder builder = new StringBuilder(reference.get(0).getFieldName());
                if (reference.size() > 1) {
                    reference
                            .subList(1, reference.size())
                            .forEach(r -> {
                                builder.append("[");
                                if (StringUtils.isNotBlank(r.getFieldName())){
                                    builder.append(r.getFieldName());
                                }});
                    builder.append(StringUtils.repeat("]", reference.size() - 1));
                }
                String field = builder.toString();
                Object value = cause instanceof InvalidFormatException ?
                        ((InvalidFormatException) cause).getValue() :
                        ((InvalidEnumException) cause).getRejected();
                Class type = cause instanceof InvalidFormatException ?
                        ((InvalidFormatException) cause).getTargetType() :
                        ((InvalidEnumException) cause).getEnumClass();
                String message;
                if (type.isEnum()) {
                    message = messageSource.getMessage("typeMismatch.java.lang.Enum",
                            new Object[]{value, type.getSimpleName(),
                                    StringUtils.join(EnumUtil.getEnumConstants(type), ", ")}, Locale.ENGLISH);
                } else {
                    message = messageSource.getMessage("typeMismatch", new Object[]{value}, Locale.ENGLISH);
                }
                errors.add(new Error(field, message));
            }
        } else if (cause instanceof PropertyBindingException) {
            PropertyBindingException ex = (PropertyBindingException) cause;
            String field = ex.getPropertyName();
            String expected = StringUtils.join(ex.getKnownPropertyIds(), ", ");
            String error = messageSource.getMessage("field.invalid", new Object[]{expected}, Locale.ENGLISH);
            errors.add(new Error(field, error));
        }

        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
    }


    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, RequestNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleValidationException(Exception e) {
        //logger.error("{}",e.getLocalizedMessage());

        BindingResult result;
        if (e instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        } else {
            result = ((RequestNotValidException) e).getBindingResult();
        }
        List<Error> errors = new ArrayList<>();
        result.getGlobalErrors()
                .forEach(error ->
                        errors.add(new Error(error.getObjectName(), messageSource.getMessage(error, Locale.ENGLISH))));
        result.getFieldErrors()
                .forEach(
                        error -> {
                            FieldError resolvable = error;
                            if (error.isBindingFailure()) {
                                String[] codes = error.getCodes();
                                Object[] args = new Object[3];
                                args[0] = error.getRejectedValue();
                                Class type = result.getFieldType(error.getField());
                                if (type != null && type.isEnum()) {
                                    codes = new String[]{"typeMismatch.java.lang.Enum"};
                                    args[1] = type.getSimpleName();
                                    args[2] = StringUtils.join(EnumUtil.getEnumConstants(type), ", ");
                                }
                                resolvable = new FieldError(error.getObjectName(), error.getField(),
                                        error.getRejectedValue(), error.isBindingFailure(),
                                        codes, args, error.getDefaultMessage());
                            }
                            errors.add(new Error(error.getField(), messageSource.getMessage(resolvable, Locale.ENGLISH)));
                        }
                );
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Bad Request", errors);
       // return errors;
    }

    @ExceptionHandler(BadRequestException.class)
    public ApiResponse<Void> handleExceptionAdvice(BadRequestException e) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<Void> handleExceptionAdvice(NotFoundException e) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null);
    }

    @ExceptionHandler(DuplicateException.class)
    public ApiResponse<Void> handleExceptionAdvice(DuplicateException e) {
        return new ApiResponse<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> handleExceptionAdvice(AccessDeniedException e) {
        return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse<Void> handleExceptionAdvice(AuthenticationException e) {
        return new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "Invalid username on password", null);
    }


}
