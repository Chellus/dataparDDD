package com.chellus.TiendaCRUD.Exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Client " + id + " not found");
    }
}
