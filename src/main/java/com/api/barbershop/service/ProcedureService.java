package com.api.barbershop.service;

import com.api.barbershop.dto.procedure.GetProcedureDTO;
import com.api.barbershop.dto.procedure.PostProcedureDTO;
import com.api.barbershop.dto.procedure.PutProcedureDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Procedure;
import com.api.barbershop.repository.ProcedureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedureService {
    @Autowired ProcedureRepository procedureRepository;

    @Transactional
    public GetProcedureDTO postProcedure(PostProcedureDTO data){
        Procedure procedure = new Procedure(data);
        procedureRepository.save(procedure);
        return new GetProcedureDTO(procedure);
    }

    public List<GetProcedureDTO> getAllProcedures(){
        return procedureRepository.findAllByAvailableAndSortByName().stream().map(GetProcedureDTO::new).toList();
    }

    public GetProcedureDTO getProcedureById(Long id){
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found."));

        if(Boolean.FALSE.equals(procedure.getIsAvailable())){
            throw new BusinessRuleException("Procedure is deleted.");
        }

        return new GetProcedureDTO(procedure);
    }

    @Transactional
    public GetProcedureDTO putProcedureById(Long id, PutProcedureDTO data){
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found."));

        if(Boolean.FALSE.equals(procedure.getIsAvailable())){
            throw new BusinessRuleException("Procedure is deleted.");
        }

        procedure.update(data);
        return new GetProcedureDTO(procedure);
    }

    @Transactional
    public void deleteProcedureById(Long id){
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found."));

        if(Boolean.FALSE.equals(procedure.getIsAvailable())){
            throw new BusinessRuleException("Procedure is already deleted.");
        }

        procedure.delete();
    }

    @Transactional
    public GetProcedureDTO reactivateProcedureById(Long id){
        Procedure procedure = procedureRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Procedure not found."));

        if(Boolean.TRUE.equals(procedure.getIsAvailable())){
            throw new BusinessRuleException("Procedure is activate.");
        }

        procedure.reactivate();
        return new GetProcedureDTO(procedure);
    }
}
