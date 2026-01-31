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
    public ResponseEntity<BarberDetailsResponseDTO> create(@RequestBody @Valid BarberCreateDTO data, UriComponentsBuilder builder){
        BarberDetailsResponseDTO barber = barberService.update(data);
        URI uri = builder.path("/{id}").buildAndExpand(barber.id()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @GetMapping
    public ResponseEntity<List<BarberSummaryResponseDTO>> findAll(){
        return ResponseEntity.ok(barberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(barberService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> update(@PathVariable Long id, @RequestBody @Valid BarberUpdateDTO data){
        return ResponseEntity.ok(barberService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        barberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BarberDetailsResponseDTO> reactivate(@PathVariable Long id){
        return ResponseEntity.ok(barberService.reactivate(id));
    }
}
