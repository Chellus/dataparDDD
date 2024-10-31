package com.chellus.TiendaCRUD.Exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order with id " + id + "Not Found");
    }
}
