package com.chellus.TiendaCRUD.Client;

import com.chellus.TiendaCRUD.CustomerOrder.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDTO createClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setAddress(clientDTO.getAddress());
        client.setPhone(clientDTO.getPhone());

        Client savedClient = clientRepository.save(client);
        return toClientDTO(savedClient);
    }

    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(this::toClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        return toClientDTO(client);
    }

    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        existingClient.setName(clientDTO.getName());
        existingClient.setAddress(clientDTO.getAddress());
        existingClient.setPhone(clientDTO.getPhone());

        Client updatedClient = clientRepository.save(existingClient);
        return toClientDTO(updatedClient);
    }

    public ClientDTO deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        ClientDTO clientDTO = toClientDTO(client);
        clientRepository.delete(client);

        return clientDTO;
    }

    private ClientDTO toClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhone(client.getPhone());

        // populate customerOrderIds
        List<Long> customerOrdersId = new ArrayList<>();

        for (CustomerOrder order : client.getOrders()) {
            customerOrdersId.add(order.getId());
        }

        clientDTO.setCustomerOrdersId(customerOrdersId);

        return clientDTO;
    }
}
