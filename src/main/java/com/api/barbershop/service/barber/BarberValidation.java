package com.api.barbershop.service.barber;

import com.api.barbershop.dto.barber.PostBarberDTO;
import com.api.barbershop.dto.barber.PutBarberDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Barber;
import com.api.barbershop.repository.BarberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BarberValidation {
    @Autowired BarberRepository barberRepository;

    public void validateUniqueFields(PostBarberDTO fields){
        if(barberRepository.existsByPhone(fields.phone())){
            throw new BusinessRuleException("There is already a Barber registered with the provided phone.");
        }

        if(barberRepository.existsByCpf(fields.cpf())){
            throw new BusinessRuleException("There is already a Barber registered with the provided cpf.");
        }
    }

    public void validateUniqueFields(PutBarberDTO fields, Long id){
        if(barberRepository.existsByPhoneAndIdNot(fields.phone(), id)){
            throw new BusinessRuleException("There is already a Barber registered with the provided phone.");
        }

        if(barberRepository.existsByCpfAndIdNot(fields.cpf(), id)){
            throw new BusinessRuleException("There is already a Barber registered with the provided cpf.");
        }
    }

    public Barber validateBarber(Long id, BarberAction action){
        Barber barber = findIdOrFail(id);

        switch (action){
            case ACTIVE_CHECK -> {
                if(Boolean.FALSE.equals(barber.getIsAvailable())){
                    throw new BusinessRuleException("Barber is not available with id " + id + ".");
                }
            }
            case DELETE -> {
                if(Boolean.FALSE.equals(barber.getIsAvailable())){
                    throw new BusinessRuleException("Barber is already not available with id " + id + ".");
                }
            }
            case REACTIVATE -> {
                if(Boolean.TRUE.equals(barber.getIsAvailable())){
                    throw new BusinessRuleException("Barber is already available with id " + id + ".");
                }
            }
        }
        return barber;
    }

    private Barber findIdOrFail(Long id){
        return barberRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Barber not found with id " + id + "."));
    }
}
