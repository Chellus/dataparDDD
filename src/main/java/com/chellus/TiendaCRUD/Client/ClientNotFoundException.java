package com.chellus.TiendaCRUD.Client;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(Long id) {
        super("Client " + id + " not found");
    }
}
