package com.api.barbershop.service.barber;

import com.api.barbershop.dto.barber.BarberDetailsResponseDTO;
import com.api.barbershop.dto.barber.BarberSummaryResponseDTO;
import com.api.barbershop.dto.barber.BarberCreateDTO;
import com.api.barbershop.dto.barber.BarberUpdateDTO;
import com.api.barbershop.model.Barber;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.BarberRepository;
import com.api.barbershop.service.unit.UnitAction;
import com.api.barbershop.service.unit.UnitValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BarberService {
    @Autowired BarberRepository barberRepository;
    @Autowired BarberValidation barberValidation;
    @Autowired UnitValidation unitValidation;

    @Transactional
    public BarberDetailsResponseDTO update(BarberCreateDTO data){
        barberValidation.validateUniqueFields(data);
        Unit unit = unitValidation.validateUnit(data.unitId(), UnitAction.ACTIVE_CHECK);
        Barber barber = new Barber(data, unit);
        barberRepository.save(barber);
        return new BarberDetailsResponseDTO(barber);
    }

    public List<BarberSummaryResponseDTO> findAll(){
        return barberRepository.findAllByAvailableAndSortByName().stream().map(BarberSummaryResponseDTO::new).toList();
    }

    public BarberDetailsResponseDTO findById(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.ACTIVE_CHECK);
        return new BarberDetailsResponseDTO(barber);
    }

    @Transactional
    public BarberDetailsResponseDTO update(Long id, BarberUpdateDTO data){
        Barber barber = barberValidation.validateBarber(id, BarberAction.ACTIVE_CHECK);
        barberValidation.validateUniqueFields(data, id);
        Unit unit = null;

        if(data.unitId() != null) {
            unit = unitValidation.validateUnit(data.unitId(), UnitAction.ACTIVE_CHECK);
        }

        barber.update(data, unit);
        return new BarberDetailsResponseDTO(barber);
    }

    @Transactional
    public void delete(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.DELETE);
        barber.delete();
    }

    @Transactional
    public BarberDetailsResponseDTO reactivate(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.REACTIVATE);
        barber.reactivate();
        return new BarberDetailsResponseDTO(barber);
    }
}
