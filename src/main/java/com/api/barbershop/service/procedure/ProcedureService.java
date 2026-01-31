package com.api.barbershop.service.procedure;

import com.api.barbershop.dto.procedure.GetProcedureDTO;
import com.api.barbershop.dto.procedure.PostProcedureDTO;
import com.api.barbershop.dto.procedure.PutProcedureDTO;
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
    public GetProcedureDTO postProcedure(PostProcedureDTO data){
        procedureValidation.validateUniqueFields(data);
        Procedure procedure = new Procedure(data);
        procedureRepository.save(procedure);
        return new GetProcedureDTO(procedure);
    }

    public List<GetProcedureDTO> getAllProcedures(){
        return procedureRepository.findAllByAvailableAndSortByName().stream().map(GetProcedureDTO::new).toList();
    }

    public GetProcedureDTO getProcedureById(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.ACTIVE_CHECK);
        return new GetProcedureDTO(procedure);
    }

    @Transactional
    public GetProcedureDTO putProcedureById(Long id, PutProcedureDTO data){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.ACTIVE_CHECK);
        procedureValidation.validateUniqueFields(data, id);
        procedure.update(data);
        return new GetProcedureDTO(procedure);
    }

    @Transactional
    public void deleteProcedureById(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.DELETE);
        procedure.delete();
    }

    @Transactional
    public GetProcedureDTO reactivateProcedureById(Long id){
        Procedure procedure = procedureValidation.validateProcedure(id, ProcedureAction.REACTIVATE);
        procedure.reactivate();
        return new GetProcedureDTO(procedure);
    }
}
