package com.api.barbershop.service;

import com.api.barbershop.dto.unit.GetUnitDTO;
import com.api.barbershop.dto.unit.PostUnitDTO;
import com.api.barbershop.dto.unit.PutUnitDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Transactional
    public GetUnitDTO postAnUnit(PostUnitDTO data){
        Unit unit = new Unit(data);
        unitRepository.save(unit);
        return new GetUnitDTO(unit);
    }

    public List<GetUnitDTO> getAllUnits(){
        return unitRepository.findAllByAvailable().stream().map(GetUnitDTO::new).toList();
    }

    public GetUnitDTO getUnitById(Long id){
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

        if(Boolean.FALSE.equals(unit.getIsAvailable())){
            throw new BusinessRuleException("Unit is deleted.");
        }

        return new GetUnitDTO(unit);
    }

    @Transactional
    public GetUnitDTO putUnitById(Long id, PutUnitDTO data){
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

        if(Boolean.FALSE.equals(unit.getIsAvailable())){
            throw new BusinessRuleException("Unit is deleted.");
        }

        unit.update(data);
        return new GetUnitDTO(unit);
    }

    @Transactional
    public void deleteUnitById(Long id){
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

        if(Boolean.FALSE.equals(unit.getIsAvailable())){
            throw new BusinessRuleException("Unit is already deleted.");
        }

        unit.delete();
    }

    @Transactional
    public GetUnitDTO reactivateUnitById(Long id){
        Unit unit = unitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

        if(Boolean.TRUE.equals(unit.getIsAvailable())){
            throw new BusinessRuleException("Unit is activate.");
        }

        unit.reactivate();
        return new GetUnitDTO(unit);
    }
}
