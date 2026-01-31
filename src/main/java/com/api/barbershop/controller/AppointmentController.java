package com.api.barbershop.controller;

import com.api.barbershop.dto.appointment.AppointmentDetailsResponseDTO;
import com.api.barbershop.dto.appointment.AppointmentSummaryResponseDTO;
import com.api.barbershop.dto.appointment.AppointmentCreateDTO;
import com.api.barbershop.dto.appointment.AppointmentUpdateDTO;
import com.api.barbershop.service.appointment.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {
    @Autowired AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDetailsResponseDTO> postAppointment(@RequestBody @Valid AppointmentCreateDTO data, UriComponentsBuilder builder){
        AppointmentDetailsResponseDTO appointment = appointmentService.postAppointment(data);
        URI uri = builder.path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentSummaryResponseDTO>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> getAppointmentById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> putAppointmentById(@PathVariable Long id, @RequestBody AppointmentUpdateDTO data){
        return ResponseEntity.ok(appointmentService.putAppointmentById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable Long id){
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> reactivateAppointmentById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.reactivateAppointmentById(id));
    }
}
