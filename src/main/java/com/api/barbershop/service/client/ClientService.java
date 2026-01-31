package com.api.barbershop.service.client;

import com.api.barbershop.dto.client.GetClientDetailsDTO;
import com.api.barbershop.dto.client.GetClientSimpleDTO;
import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
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
    public GetClientDetailsDTO postClient(PostClientDTO data){
        clientValidation.validateUniqueFields(data);
        Client client = new Client(data);
        clientRepository.save(client);
        return new GetClientDetailsDTO(client);
    }

    public List<GetClientSimpleDTO> getAllClients(){
        return clientRepository.findAllByAvailableAndSortByName().stream().map(GetClientSimpleDTO::new).toList();
    }

    public GetClientDetailsDTO getClientById(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.ACTIVE_CHECK);
        return new GetClientDetailsDTO(client);
    }

    @Transactional
    public GetClientDetailsDTO putClientById(Long id, PutClientDTO data){
        Client client = clientValidation.validateClient(id, ClientAction.ACTIVE_CHECK);
        clientValidation.validateUniqueFields(data, id);
        client.update(data);
        return new GetClientDetailsDTO(client);
    }

    @Transactional
    public void deleteClientById(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.DELETE);
        client.delete();
    }

    @Transactional
    public GetClientDetailsDTO reactivateClientById(Long id){
        Client client = clientValidation.validateClient(id, ClientAction.REACTIVATE);
        client.reactivate();
        return new GetClientDetailsDTO(client);
    }
}
