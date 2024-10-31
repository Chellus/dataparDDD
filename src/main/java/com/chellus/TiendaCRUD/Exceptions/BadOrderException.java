package com.chellus.TiendaCRUD.Exceptions;

public class BadOrderException extends RuntimeException {
    public BadOrderException(String message) {
        super(message);
    }
}
