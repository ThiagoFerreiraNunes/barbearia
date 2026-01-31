package com.api.barbershop.service.barber;

import com.api.barbershop.dto.barber.GetBarberDetailsDTO;
import com.api.barbershop.dto.barber.GetBarberSimpleDTO;
import com.api.barbershop.dto.barber.PostBarberDTO;
import com.api.barbershop.dto.barber.PutBarberDTO;
import com.api.barbershop.model.Barber;
import com.api.barbershop.model.Unit;
import com.api.barbershop.repository.BarberRepository;
import com.api.barbershop.service.unit.UnitValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BarberService {
    @Autowired BarberRepository barberRepository;
    @Autowired BarberValidation barberValidation;
    @Autowired UnitValidation unitValidation;

    @Transactional
    public GetBarberDetailsDTO postBarber(PostBarberDTO data){
        barberValidation.validateUniqueFields(data);
        Unit unit = unitValidation.validateActive(data.unitId());
        Barber barber = new Barber(data, unit);
        barberRepository.save(barber);
        return new GetBarberDetailsDTO(barber);
    }

    public List<GetBarberSimpleDTO> getAllBarbers(){
        return barberRepository.findAllByAvailableAndSortByName().stream().map(GetBarberSimpleDTO::new).toList();
    }

    public GetBarberDetailsDTO getBarberById(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.ACTIVE_CHECK);
        return new GetBarberDetailsDTO(barber);
    }

    @Transactional
    public GetBarberDetailsDTO putBarberById(Long id, PutBarberDTO data){
        Barber barber = barberValidation.validateBarber(id, BarberAction.ACTIVE_CHECK);
        barberValidation.validateUniqueFields(data, id);
        Unit unit = null;

        if(data.unitId() != null) {
            unit = unitValidation.validateActive(data.unitId());
        }

        barber.update(data, unit);
        return new GetBarberDetailsDTO(barber);
    }

    @Transactional
    public void deleteBarberById(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.DELETE);
        barber.delete();
    }

    @Transactional
    public GetBarberDetailsDTO reactivateBarberById(Long id){
        Barber barber = barberValidation.validateBarber(id, BarberAction.REACTIVATE);
        barber.reactivate();
        return new GetBarberDetailsDTO(barber);
    }
}
