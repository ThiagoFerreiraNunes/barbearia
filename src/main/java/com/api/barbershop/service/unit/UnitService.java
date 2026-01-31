package com.api.barbershop.service.unit;

import com.api.barbershop.dto.unit.UnitResponseDTO;
import com.api.barbershop.dto.unit.UnitCreateDTO;
import com.api.barbershop.dto.unit.UnitUpdateDTO;
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
    public UnitResponseDTO create(UnitCreateDTO data){
        unitValidation.validateUniqueFields(data);
        Unit unit = new Unit(data);
        unitRepository.save(unit);
        return new UnitResponseDTO(unit);
    }

    public List<UnitResponseDTO> findAll(){
        return unitRepository.findAllByAvailable().stream().map(UnitResponseDTO::new).toList();
    }

    public UnitResponseDTO findById(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.ACTIVE_CHECK);
        return new UnitResponseDTO(unit);
    }

    @Transactional
    public UnitResponseDTO update(Long id, UnitUpdateDTO data){
        Unit unit = unitValidation.validateUnit(id, UnitAction.ACTIVE_CHECK);
        unitValidation.validateUniqueFields(data, id);
        unit.update(data);
        return new UnitResponseDTO(unit);
    }

    @Transactional
    public void delete(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.DELETE);
        unit.delete();
    }

    @Transactional
    public UnitResponseDTO reactivate(Long id){
        Unit unit = unitValidation.validateUnit(id, UnitAction.REACTIVATE);
        unit.reactivate();
        return new UnitResponseDTO(unit);
    }
}
