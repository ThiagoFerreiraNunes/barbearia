package com.api.barbershop.service.client;

import com.api.barbershop.dto.client.ClientDetailsResponseDTO;
import com.api.barbershop.dto.client.ClientSummaryResponseDTO;
import com.api.barbershop.dto.client.ClientCreateDTO;
import com.api.barbershop.dto.client.ClientUpdateDTO;
import com.api.barbershop.model.Client;
import com.api.barbershop.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    @Autowired ClientRepository clientRepository;
    @Autowired ClientValidation clientValidation;

    @Transactional
    public ClientDetailsResponseDTO update(ClientCreateDTO data){
        clientValidation.validateUniqueFields(data);
        Client client = new Client(data);
        clientRepository.save(client);
        return new ClientDetailsResponseDTO(client);
    }

    public List<ClientSummaryResponseDTO> findAll(){
        return clientRepository.findAllByAvailableAndSortByName().stream().map(ClientSummaryResponseDTO::new).toList();
    }

    public ClientDetailsResponseDTO findById(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.ACTIVE_CHECK);
        return new ClientDetailsResponseDTO(client);
    }

    @Transactional
    public ClientDetailsResponseDTO update(Long id, ClientUpdateDTO data){
        Client client = clientValidation.validateClient(id, ClientAction.ACTIVE_CHECK);
        clientValidation.validateUniqueFields(data, id);
        client.update(data);
        return new ClientDetailsResponseDTO(client);
    }

    @Transactional
    public void delete(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.DELETE);
        client.delete();
    }

    @Transactional
    public ClientDetailsResponseDTO reactivate(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.REACTIVATE);
        client.reactivate();
        return new ClientDetailsResponseDTO(client);
    }
}
