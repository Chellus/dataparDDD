package com.chellus.TiendaCRUD.controllers;

import com.chellus.TiendaCRUD.models.Client;
import com.chellus.TiendaCRUD.models.ClientDTO;
import com.chellus.TiendaCRUD.models.CustomerOrder;
import com.chellus.TiendaCRUD.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    // List all clients
    @GetMapping
    public ResponseEntity<List<ClientDTO>> listClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOs = new ArrayList<>();


        for (Client client : clients) {
            ClientDTO clientDTO = new ClientDTO();
            List<Long> ids = new ArrayList<>();
            clientDTO.setId(client.getId());
            clientDTO.setName(client.getName());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhone(client.getPhone());

            for (CustomerOrder order : client.getOrders()) {
                ids.add(order.getId());
            }
            clientDTO.setCustomerOrdersId(ids);
            clientDTOs.add(clientDTO);
        }

        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    // Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        Optional<Client> optionalClient = clientRepository.findById(id);

        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            ClientDTO clientDTO = new ClientDTO();
            List<Long> ids = new ArrayList<>();
            clientDTO.setId(client.getId());
            clientDTO.setName(client.getName());
            clientDTO.setAddress(client.getAddress());
            clientDTO.setPhone(client.getPhone());

            for (CustomerOrder order : client.getOrders()) {
                ids.add(order.getId());
            }
            clientDTO.setCustomerOrdersId(ids);
            return new ResponseEntity<>(clientDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    // Create a new client
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO client) {
        Client newClient = new Client();

        newClient.setName(client.getName());
        newClient.setAddress(client.getAddress());
        newClient.setPhone(client.getPhone());
        Client savedClient = clientRepository.save(newClient);
        client.setId(savedClient.getId());

        return new ResponseEntity<>(client, HttpStatus.CREATED);
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
