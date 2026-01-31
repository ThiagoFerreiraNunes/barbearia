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
    public ResponseEntity<AppointmentDetailsResponseDTO> create(@RequestBody @Valid AppointmentCreateDTO data, UriComponentsBuilder builder){
        AppointmentDetailsResponseDTO appointment = appointmentService.create(data);
        URI uri = builder.path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentSummaryResponseDTO>> findAll(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> update(@PathVariable Long id, @RequestBody AppointmentUpdateDTO data){
        return ResponseEntity.ok(appointmentService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        appointmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AppointmentDetailsResponseDTO> reactivate(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.reactivate(id));
    }
}
