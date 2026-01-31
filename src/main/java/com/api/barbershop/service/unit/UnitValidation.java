package com.api.barbershop.service.unit;

import com.api.barbershop.dto.unit.UnitCreateDTO;
import com.api.barbershop.dto.unit.UnitUpdateDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitValidation {
    @Autowired UnitRepository unitRepository;

    public void validateUniqueFields(UnitCreateDTO fields){
        if(unitRepository.existsByPhone(fields.phone())){
            throw new BusinessRuleException("There is already a Unit registered with the provided phone.");
        }

        if(unitRepository.existsByAddress(fields.address())){
            throw new BusinessRuleException("There is already a Unit registered with the provided address.");
        }
    }

    public void validateUniqueFields(UnitUpdateDTO fields, Long id){
        if(unitRepository.existsByPhoneAndIdNot(fields.phone())){
            throw new BusinessRuleException("There is already a Unit registered with the provided phone.");
        }

        if(unitRepository.existsByAddressAndIdNot(fields.address())){
            throw new BusinessRuleException("There is already a Unit registered with the provided address.");
        }
    }

    public Unit validateUnit(Long id, UnitAction action){
        Unit unit = findIdOrFail(id);

        switch (action){
            case ACTIVE_CHECK -> {
                if(Boolean.FALSE.equals(unit.getIsAvailable())){
                    throw new BusinessRuleException("Unit is not available with id " + id + ".");
                }
            }
            case DELETE -> {
                if(Boolean.FALSE.equals(unit.getIsAvailable())){
                    throw new BusinessRuleException("Unit is already not available with id " + id + ".");
                }
            }
            case REACTIVATE -> {
                if(Boolean.TRUE.equals(unit.getIsAvailable())){
                    throw new BusinessRuleException("Unit is already available with id " + id + ".");
                }
            }
        }
        return unit;
    }

    private Unit findIdOrFail(Long id){
        return unitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Unit not found with id " + id + "."));
    }

}
