package com.api.barbershop.service.procedure;

import com.api.barbershop.dto.procedure.ProcedureResponseDTO;
import com.api.barbershop.dto.procedure.ProcedureCreateDTO;
import com.api.barbershop.dto.procedure.ProcedureUpdateDTO;
import com.api.barbershop.model.Procedure;
import com.api.barbershop.repository.ProcedureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureService {
    @Autowired ProcedureRepository procedureRepository;
    @Autowired ProcedureValidation procedureValidation;

    @Transactional
    public ProcedureResponseDTO create(ProcedureCreateDTO data){
        procedureValidation.validateUniqueFields(data);
        Procedure procedure = new Procedure(data);
        procedureRepository.save(procedure);
        return new ProcedureResponseDTO(procedure);
    }

    public List<ProcedureResponseDTO> findAll(){
        return procedureRepository.findAllByAvailableAndSortByName().stream().map(ProcedureResponseDTO::new).toList();
    }

    public ProcedureResponseDTO findById(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.ACTIVE_CHECK);
        return new ProcedureResponseDTO(procedure);
    }

    @Transactional
    public ProcedureResponseDTO update(Long id, ProcedureUpdateDTO data){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.ACTIVE_CHECK);
        procedureValidation.validateUniqueFields(data, id);
        procedure.update(data);
        return new ProcedureResponseDTO(procedure);
    }

    @Transactional
    public void delete(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.DELETE);
        procedure.delete();
    }

    @Transactional
    public ProcedureResponseDTO reactivate(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.REACTIVATE);
        procedure.reactivate();
        return new ProcedureResponseDTO(procedure);
    }
}
