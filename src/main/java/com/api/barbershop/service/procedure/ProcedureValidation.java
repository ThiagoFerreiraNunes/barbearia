package com.api.barbershop.service.procedure;

import com.api.barbershop.dto.procedure.PostProcedureDTO;
import com.api.barbershop.dto.procedure.PutProcedureDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Procedure;
import com.api.barbershop.repository.ProcedureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ProcedureValidation {
    @Autowired ProcedureRepository procedureRepository;

    public void validateUniqueFields(PostProcedureDTO fields){
        if(procedureRepository.existsByName(fields.name())){
            throw new BusinessRuleException("There is already a Procedure registered with the provided name.");
        }
    }

    public void validateUniqueFields(PutProcedureDTO fields, Long id){
        if(procedureRepository.existsByNameAndIdNot(fields.name(), id)){
            throw new BusinessRuleException("There is already a Procedure registered with the provided name.");
        }
    }

    public Procedure validateProcedure(Long id, ProcedureAction action){
        Procedure procedure = findIdOrFail(id);

        switch (action){
            case ACTIVE_CHECK -> {
                if(Boolean.FALSE.equals(procedure.getIsAvailable())){
                    throw new BusinessRuleException("Procedure is not available with id " + id + ".");
                }
            }
            case DELETE -> {
                if(Boolean.FALSE.equals(procedure.getIsAvailable())){
                    throw new BusinessRuleException("Procedure is already not available with id " + id + ".");
                }
            }
            case REACTIVATE -> {
                if(Boolean.TRUE.equals(procedure.getIsAvailable())){
                    throw new BusinessRuleException("Procedure is already available with id " + id + ".");
                }
            }
        }

        return procedure;
    }

    public List<Procedure> validateActiveList(List<Long> ids){
        Set<Long> uniqueIds = new HashSet<>(ids);
        List<Procedure> procedures = procedureRepository.findAllById(uniqueIds);

        if(procedures.size() != uniqueIds.size()){
            List<Long> foundIds = procedures.stream()
                    .map(Procedure::getId)
                    .toList();

            List<Long> missingIds = new ArrayList<>(uniqueIds);
            missingIds.removeAll(foundIds);

            throw new EntityNotFoundException("Procedures not found with ids " + missingIds + ".");
        }

        List<Long> unavailableIds = procedures.stream()
                .filter(p -> Boolean.FALSE.equals(p.getIsAvailable()))
                .map(Procedure::getId)
                .toList();

        if(!unavailableIds.isEmpty()){
            throw new BusinessRuleException("Procedures are not available with ids " + unavailableIds + ".");
        }

        return procedures;
    }

    private Procedure findIdOrFail(Long id){
        return procedureRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found with id " + id + "."));
    }
}
