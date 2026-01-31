package com.api.barbershop.service.unit;

import com.api.barbershop.dto.unit.GetUnitDTO;
import com.api.barbershop.dto.unit.PostUnitDTO;
import com.api.barbershop.dto.unit.PutUnitDTO;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired UnitRepository unitRepository;
    @Autowired UnitValidation unitValidation;

    @Transactional
    public GetUnitDTO postUnit(PostUnitDTO data){
        unitValidation.validateUniqueFields(data);
        Unit unit = new Unit(data);
        unitRepository.save(unit);
        return new GetUnitDTO(unit);
    }

    public List<GetUnitDTO> getAllUnits(){
        return unitRepository.findAllByAvailable().stream().map(GetUnitDTO::new).toList();
    }

    public GetUnitDTO getUnitById(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.ACTIVE_CHECK);
        return new GetUnitDTO(unit);
    }

    @Transactional
    public GetUnitDTO putUnitById(Long id, PutUnitDTO data){
        Unit unit = unitValidation.validateUnit(id, UnitAction.ACTIVE_CHECK);
        unitValidation.validateUniqueFields(data, id);
        unit.update(data);
        return new GetUnitDTO(unit);
    }

    @Transactional
    public void deleteUnitById(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.DELETE);
        unit.delete();
    }

    @Transactional
    public GetUnitDTO reactivateUnitById(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.REACTIVATE);
        unit.reactivate();
        return new GetUnitDTO(unit);
    }
}
