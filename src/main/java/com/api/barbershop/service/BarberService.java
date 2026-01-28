package com.api.barbershop.service;

import com.api.barbershop.dto.barber.GetBarberDetailsDTO;
import com.api.barbershop.dto.barber.GetBarberSimpleDTO;
import com.api.barbershop.dto.barber.PostBarberDTO;
import com.api.barbershop.dto.barber.PutBarberDTO;
import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Barber;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.BarberRepository;
import com.api.barbershop.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BarberService {
    @Autowired BarberRepository barberRepository;
    @Autowired UnitRepository unitRepository;

    @Transactional
    public GetBarberDetailsDTO postBarber(PostBarberDTO data){
        Unit unit = unitRepository.findById(data.unitId()).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

        if(Boolean.FALSE.equals(unit.getIsAvailable())){
            throw new BusinessRuleException("Unit is deleted.");
        }

        Barber barber = new Barber(data, unit);
        barberRepository.save(barber);
        return new GetBarberDetailsDTO(barber);
    }

    public List<GetBarberSimpleDTO> getAllBarbers(){
        return barberRepository.findAllByAvailableAndSortByName().stream().map(GetBarberSimpleDTO::new).toList();
    }

    public GetBarberDetailsDTO getBarberById(Long id){
        Barber barber = barberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Barber not found."));

        if(Boolean.FALSE.equals(barber.getIsAvailable())){
            throw new BusinessRuleException("Barber is deleted.");
        }

        return new GetBarberDetailsDTO(barber);
    }

    @Transactional
    public GetBarberDetailsDTO putBarberById(Long id, PutBarberDTO data){
        Barber barber = barberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Barber not found."));

        if(Boolean.FALSE.equals(barber.getIsAvailable())){
            throw new BusinessRuleException("Barber is deleted.");
        }

        Unit unit = null;

        if(data.unitId() != null) {
            unit = unitRepository.findById(data.unitId()).orElseThrow(() -> new EntityNotFoundException("Unit not found."));

            if(Boolean.FALSE.equals(unit.getIsAvailable())){
                throw new BusinessRuleException("Unit is deleted.");
            }
        }

        barber.update(data, unit);
        return new GetBarberDetailsDTO(barber);
    }

    @Transactional
    public void deleteBarberById(Long id){
        Barber barber = barberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Barber not found."));

        if(Boolean.FALSE.equals(barber.getIsAvailable())){
            throw new BusinessRuleException("Barber is already deleted.");
        }

        barber.delete();
    }

    @Transactional
    public GetBarberDetailsDTO reactivateBarberById(Long id){
        Barber barber = barberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Barber not found."));

        if(Boolean.TRUE.equals(barber.getIsAvailable())){
            throw new BusinessRuleException("Barber is already activate.");
        }

        barber.reactivate();
        return new GetBarberDetailsDTO(barber);
    }
}
