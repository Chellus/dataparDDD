package com.chellus.TiendaCRUD.Client;

import com.chellus.TiendaCRUD.CustomerOrder.CustomerOrderRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private CustomerOrderRepository orderRepository;

    // List all clients
    @GetMapping
    public ResponseEntity<List<ClientDTO>> listClients() {
        List<ClientDTO> clientDTOs = clientService.getAllClients();

        return new ResponseEntity<>(clientDTOs, HttpStatus.OK);
    }

    // Get client by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.getClientById(id);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    // Create a new client
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientDTO client) {
        ClientDTO clientDTO = clientService.createClient(client);
        return new ResponseEntity<>(clientDTO, HttpStatus.CREATED);
    }

    // Update an existing client by ID
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody @Valid ClientDTO client) {
        ClientDTO clientDTO = clientService.updateClient(id, client);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    // Delete a client by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
        ClientDTO clientDTO = clientService.deleteClient(id);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }
}
