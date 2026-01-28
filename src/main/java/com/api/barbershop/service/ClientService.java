package com.api.barbershop.service;

import com.api.barbershop.dto.client.GetClientDetailsDTO;
import com.api.barbershop.dto.client.GetClientSimpleDTO;
import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Client;
import com.api.barbershop.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    @Autowired ClientRepository clientRepository;

    @Transactional
    public GetClientDetailsDTO postClient(PostClientDTO data){
        Client client = new Client(data);
        clientRepository.save(client);
        return new GetClientDetailsDTO(client);
    }

    public List<GetClientSimpleDTO> getAllClients(){
        return clientRepository.findAllByAvailableAndSortByName().stream().map(GetClientSimpleDTO::new).toList();
    }

    public GetClientDetailsDTO getClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found."));

        if(Boolean.FALSE.equals(client.getIsAvailable())){
            throw new BusinessRuleException("Client is deleted.");
        }

        return new GetClientDetailsDTO(client);
    }

    @Transactional
    public GetClientDetailsDTO putClientById(Long id, PutClientDTO data){
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found."));

        if(Boolean.FALSE.equals(client.getIsAvailable())){
            throw new BusinessRuleException("Client is deleted.");
        }

        client.update(data);
        return new GetClientDetailsDTO(client);
    }

    @Transactional
    public void deleteClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found."));

        if(Boolean.FALSE.equals(client.getIsAvailable())){
            throw new BusinessRuleException("Client is already deleted.");
        }

        client.delete();
    }

    @Transactional
    public GetClientDetailsDTO reactivateClientById(Long id){
        Client client = clientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Client not found."));

        if(Boolean.TRUE.equals(client.getIsAvailable())){
            throw new BusinessRuleException("Client is already activate.");
        }

        client.reactivate();
        return new GetClientDetailsDTO(client);
    }
}
