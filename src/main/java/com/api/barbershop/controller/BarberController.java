package com.api.barbershop.controller;

import com.api.barbershop.dto.barber.GetBarberDetailsDTO;
import com.api.barbershop.dto.barber.GetBarberSimpleDTO;
import com.api.barbershop.dto.barber.PostBarberDTO;
import com.api.barbershop.dto.barber.PutBarberDTO;
import com.api.barbershop.service.BarberService;
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
    public ResponseEntity<GetBarberDetailsDTO> postBarber(@RequestBody @Valid PostBarberDTO data, UriComponentsBuilder builder){
        GetBarberDetailsDTO barber = barberService.postBarber(data);
        URI uri = builder.path("/{id}").buildAndExpand(barber.id()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @GetMapping
    public ResponseEntity<List<GetBarberSimpleDTO>> getAllBarbers(){
        return ResponseEntity.ok(barberService.getAllBarbers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetBarberDetailsDTO> getBarberById(@PathVariable Long id){
        return ResponseEntity.ok(barberService.getBarberById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetBarberDetailsDTO> putBarberById(@PathVariable Long id, @RequestBody @Valid PutBarberDTO data){
        return ResponseEntity.ok(barberService.putBarberById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarberById(@PathVariable Long id){
        barberService.deleteBarberById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetBarberDetailsDTO> reactivateBarberById(@PathVariable Long id){
        return ResponseEntity.ok(barberService.reactivateBarberById(id));
    }
}
