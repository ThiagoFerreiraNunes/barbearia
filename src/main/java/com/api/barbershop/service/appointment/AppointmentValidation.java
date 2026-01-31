package com.api.barbershop.service.appointment;

import com.api.barbershop.exeption.BusinessRuleException;
import com.api.barbershop.model.Appointment;
import com.api.barbershop.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppointmentValidation {

    @Autowired AppointmentRepository appointmentRepository;

    public Appointment validateAppointment(Long id, AppointmentAction action){
        Appointment appointment = findIdOrFail(id);

        switch (action){
            case ACTIVE_CHECK -> {
                if(Boolean.FALSE.equals(appointment.getIsAvailable())){
                    throw new BusinessRuleException("Appointment is not available with id " + id + ".");
                }
            }
            case DELETE -> {
                if(Boolean.FALSE.equals(appointment.getIsAvailable())){
                    throw new BusinessRuleException("Appointment is already not available with id " + id + ".");
                }
            }
            case REACTIVATE -> {
                if(Boolean.TRUE.equals(appointment.getIsAvailable())){
                    throw new BusinessRuleException("Appointment is already available with id " + id + ".");
                }
            }
        }

        return appointment;
    }

    private Appointment findIdOrFail(Long id){
        return appointmentRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + id + "."));
    }
}
