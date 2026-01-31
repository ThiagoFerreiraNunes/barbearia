package com.api.barbershop.service.appointment;

import com.api.barbershop.dto.appointment.GetAppointmentDetailsDTO;
import com.api.barbershop.dto.appointment.GetAppointmentSimpleDTO;
import com.api.barbershop.dto.appointment.PostAppointmentDTO;
import com.api.barbershop.dto.appointment.PutAppointmentDTO;
import com.api.barbershop.model.*;
import com.api.barbershop.repository.*;
import com.api.barbershop.service.barber.BarberAction;
import com.api.barbershop.service.barber.BarberValidation;
import com.api.barbershop.service.client.ClientAction;
import com.api.barbershop.service.client.ClientValidation;
import com.api.barbershop.service.procedure.ProcedureValidation;
import com.api.barbershop.service.unit.UnitAction;
import com.api.barbershop.service.unit.UnitValidation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {
    @Autowired AppointmentRepository appointmentRepository;
    @Autowired AppointmentValidation appointmentValidation;
    @Autowired BarberValidation barberValidation;
    @Autowired ClientValidation clientValidation;
    @Autowired UnitValidation unitValidation;
    @Autowired ProcedureValidation procedureValidation;

    @Transactional
    public GetAppointmentDetailsDTO postAppointment(PostAppointmentDTO data){
        Barber barber = barberValidation.validateBarber(data.barberId(), BarberAction.ACTIVE_CHECK);
        Client client = clientValidation.validateClient(data.clientId(), ClientAction.ACTIVE_CHECK);
        Unit unit = unitValidation.validateUnit(data.unitId(), UnitAction.ACTIVE_CHECK);
        List<Procedure> procedures = procedureValidation.validateActiveList(data.procedureIds());

        // VALIDAR SE OS BARBEIROS, DATAS E HORÁRIOS NA UNIDADE ESTÃO DISPONÍVEIS

        Appointment appointment = new Appointment(data, barber, client, unit, procedures);
        appointmentRepository.save(appointment);
        return new GetAppointmentDetailsDTO(appointment);
    }

    public List<GetAppointmentSimpleDTO> getAllAppointments(){
        return appointmentRepository.findAllByAvailableAndSortByDate().stream().map(GetAppointmentSimpleDTO::new).toList();
    }

    public GetAppointmentDetailsDTO getAppointmentById(Long id){
        Appointment appointment = appointmentValidation.validateAppointment(id, AppointmentAction.ACTIVE_CHECK);
        return new GetAppointmentDetailsDTO(appointment);
    }

    @Transactional
    public GetAppointmentDetailsDTO putAppointmentById(Long id, PutAppointmentDTO data){
        Appointment appointment = appointmentValidation.validateAppointment(id, AppointmentAction.ACTIVE_CHECK);
        Barber barber = barberValidation.validateBarber(data.barberId(), BarberAction.ACTIVE_CHECK);
        Client client = clientValidation.validateClient(data.clientId(), ClientAction.ACTIVE_CHECK);
        Unit unit = unitValidation.validateUnit(data.unitId(), UnitAction.ACTIVE_CHECK);
        List<Procedure> procedures = procedureValidation.validateActiveList(data.procedureIds());

        // VALIDAR SE OS BARBEIROS, DATAS E HORÁRIOS NA UNIDADE ESTÃO DISPONÍVEIS

        appointment.update(data, barber, client, unit, procedures);
        return new GetAppointmentDetailsDTO(appointment);
    }

    @Transactional
    public void deleteAppointmentById(Long id){
        Appointment appointment = appointmentValidation.validateAppointment(id, AppointmentAction.DELETE);
        appointment.delete();
    }

    @Transactional
    public GetAppointmentDetailsDTO reactivateAppointmentById(Long id){
        Appointment appointment = appointmentValidation.validateAppointment(id, AppointmentAction.REACTIVATE);
        appointment.reactivate();
        return new GetAppointmentDetailsDTO(appointment);
    }
}
