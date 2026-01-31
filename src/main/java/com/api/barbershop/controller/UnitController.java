package com.api.barbershop.controller;

import com.api.barbershop.dto.unit.UnitResponseDTO;
import com.api.barbershop.dto.unit.UnitCreateDTO;
import com.api.barbershop.dto.unit.UnitUpdateDTO;
import com.api.barbershop.service.unit.UnitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {
    @Autowired UnitService unitService;

    @PostMapping
    public ResponseEntity<UnitResponseDTO> create(@RequestBody @Valid UnitCreateDTO data, UriComponentsBuilder builder){
        UnitResponseDTO unit = unitService.create(data);
        URI uri = builder.path("/{id}").buildAndExpand(unit.id()).toUri();
        return ResponseEntity.created(uri).body(unit);
    }

    @GetMapping
    public ResponseEntity<List<UnitResponseDTO>> findAll(){
        return ResponseEntity.ok(unitService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> update(@PathVariable Long id, @RequestBody UnitUpdateDTO data){
        return ResponseEntity.ok(unitService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        unitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> reactivate(@PathVariable Long id){
        return ResponseEntity.ok(unitService.reactivate(id));
    }
}
