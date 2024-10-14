package com.chellus.TiendaCRUD.controllers;

import com.chellus.TiendaCRUD.models.Client;
import com.chellus.TiendaCRUD.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    // List all clients
    @GetMapping
    public ResponseEntity<List<Client>> listClients() {
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    // Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);

        return client.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    // Create a new client
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client newClient = clientRepository.save(client);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    // Update an existing client by ID
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Optional<Client> existingClient = clientRepository.findById(id);
        if (existingClient.isPresent()) {
            Client updatedClient = existingClient.get();
            updatedClient.setName(client.getName());
            updatedClient.setAddress(client.getAddress());
            updatedClient.setPhone(client.getPhone());
            updatedClient.setOrders(client.getOrders());
            clientRepository.save(updatedClient);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            clientRepository.delete(client.get());
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
