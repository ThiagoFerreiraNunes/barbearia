package com.api.barbershop.service;

import com.api.barbershop.dto.client.GetClientDetailsDTO;
import com.api.barbershop.dto.client.GetClientSimpleDTO;
import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Client;
import com.api.barbershop.model.Plan;
import com.api.barbershop.repository.ClientRepository;
import com.api.barbershop.repository.PlanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {
    @Autowired ClientRepository clientRepository;
    @Autowired PlanRepository planRepository;

    @Transactional
    public GetClientDetailsDTO postClient(PostClientDTO data){
        Plan plan = planRepository.findById(data.planId()).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

        if(Boolean.FALSE.equals(plan.getIsAvailable())){
            throw new BusinessRuleException("Plan is deleted.");
        }

        Client client = new Client(data, plan);
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

        Plan plan = null;

        if(data.planId() != null) {
            plan = planRepository.findById(data.planId()).orElseThrow(() -> new EntityNotFoundException("Plan not found."));

            if(Boolean.FALSE.equals(plan.getIsAvailable())){
                throw new BusinessRuleException("Plan is deleted.");
            }
        }

        client.update(data, plan);
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
