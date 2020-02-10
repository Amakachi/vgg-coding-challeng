package com.vgg.exceptions;

import org.apache.commons.lang.StringUtils;


public class InvalidEnumException extends RuntimeException {

    private Object rejected;
    private Object[] expected;
    private Class<? extends Enum> enumClass;

    public InvalidEnumException(Class<? extends Enum> enumClass, Object rejected, Object[] expected) {
        super();
        this.enumClass = enumClass;
        this.rejected = rejected;
        this.expected = expected;
    }

    public Class<? extends Enum> getEnumClass() {
        return enumClass;
    }

    public Object getRejected() {
        return rejected;
    }

    public Object[] getExpected() {
        return expected;
    }

    public String getMessage() {
        return String.format("'%s' is not a valid %s. Expected: [%s]",
                rejected.toString(), enumClass.getSimpleName(), StringUtils.join(expected, ", "));
    }
}
