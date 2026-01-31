package com.api.barbershop.controller;

import com.api.barbershop.dto.appointment.GetAppointmentDetailsDTO;
import com.api.barbershop.dto.appointment.GetAppointmentSimpleDTO;
import com.api.barbershop.dto.appointment.PostAppointmentDTO;
import com.api.barbershop.dto.appointment.PutAppointmentDTO;
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
    public ResponseEntity<GetAppointmentDetailsDTO> postAppointment(@RequestBody @Valid PostAppointmentDTO data, UriComponentsBuilder builder){
        GetAppointmentDetailsDTO appointment = appointmentService.postAppointment(data);
        URI uri = builder.path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @GetMapping
    public ResponseEntity<List<GetAppointmentSimpleDTO>> getAllAppointments(){
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAppointmentDetailsDTO> getAppointmentById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetAppointmentDetailsDTO> putAppointmentById(@PathVariable Long id, @RequestBody PutAppointmentDTO data){
        return ResponseEntity.ok(appointmentService.putAppointmentById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable Long id){
        appointmentService.deleteAppointmentById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetAppointmentDetailsDTO> reactivateAppointmentById(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.reactivateAppointmentById(id));
    }
}
