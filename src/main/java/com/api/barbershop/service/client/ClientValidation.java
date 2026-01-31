package com.api.barbershop.service.client;

import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Client;
import com.api.barbershop.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientValidation {
    @Autowired ClientRepository clientRepository;

    public void validateUniqueFields(PostClientDTO fields){
        if(clientRepository.existsByPhone(fields.phone())){
            throw new BusinessRuleException("There is already a Client registered with the provided phone.");
        }

        if(clientRepository.existsByCpf(fields.cpf())){
            throw new BusinessRuleException("There is already a Client registered with the provided cpf.");
        }
    }

    public void validateUniqueFields(PutClientDTO fields, Long id){
        if(clientRepository.existsByPhoneAndIdNot(fields.phone(), id)){
            throw new BusinessRuleException("There is already a Client registered with the provided phone.");
        }

        if(clientRepository.existsByCpfAndIdNot(fields.cpf(), id)){
            throw new BusinessRuleException("There is already a Client registered with the provided cpf.");
        }
    }

    public Client validateClient(Long id, ClientAction action){
        Client client = findIdOrFail(id);

        switch (action){
            case ACTIVE_CHECK -> {
                if(Boolean.FALSE.equals(client.getIsAvailable())){
                    throw new BusinessRuleException("Client is not available with id " + id + ".");
                }
            }
            case DELETE -> {
                if(Boolean.FALSE.equals(client.getIsAvailable())){
                    throw new BusinessRuleException("Client is already not available with id " + id + ".");
                }
            }
            case REACTIVATE -> {
                if(Boolean.TRUE.equals(client.getIsAvailable())){
                    throw new BusinessRuleException("Client is already available with id " + id + ".");
                }
            }
        }

        return client;
    }

    private Client findIdOrFail(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id " + id + "."));
    }
}
