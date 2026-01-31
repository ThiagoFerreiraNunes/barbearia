package com.api.barbershop.controller;

import com.api.barbershop.dto.barber.BarberDetailsResponseDTO;
import com.api.barbershop.dto.barber.BarberSummaryResponseDTO;
import com.api.barbershop.dto.barber.BarberCreateDTO;
import com.api.barbershop.dto.barber.BarberUpdateDTO;
import com.api.barbershop.service.barber.BarberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/barbers")
public class BarberController {
    @Autowired BarberService barberService;

    @PostMapping
    public ResponseEntity<BarberDetailsResponseDTO> postBarber(@RequestBody @Valid BarberCreateDTO data, UriComponentsBuilder builder){
        BarberDetailsResponseDTO barber = barberService.postBarber(data);
        URI uri = builder.path("/{id}").buildAndExpand(barber.id()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @GetMapping
    public ResponseEntity<List<BarberSummaryResponseDTO>> getAllBarbers(){
        return ResponseEntity.ok(barberService.getAllBarbers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> getBarberById(@PathVariable Long id){
        return ResponseEntity.ok(barberService.getBarberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> putBarberById(@PathVariable Long id, @RequestBody @Valid BarberUpdateDTO data){
        return ResponseEntity.ok(barberService.putBarberById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarberById(@PathVariable Long id){
        barberService.deleteBarberById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> reactivateBarberById(@PathVariable Long id){
        return ResponseEntity.ok(barberService.reactivateBarberById(id));
    }
}
