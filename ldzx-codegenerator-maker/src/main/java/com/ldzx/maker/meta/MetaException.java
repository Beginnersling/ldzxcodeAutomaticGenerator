package com.ldzx.maker.meta;

public class MetaException extends RuntimeException{
    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }


    public MetaException(String message) {
        super(message);
    }
}
