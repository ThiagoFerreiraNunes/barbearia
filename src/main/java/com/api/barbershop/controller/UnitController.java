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
    public ResponseEntity<UnitResponseDTO> postUnit(@RequestBody @Valid UnitCreateDTO data, UriComponentsBuilder builder){
        UnitResponseDTO unit = unitService.postUnit(data);
        URI uri = builder.path("/{id}").buildAndExpand(unit.id()).toUri();
        return ResponseEntity.created(uri).body(unit);
    }

    @GetMapping
    public ResponseEntity<List<UnitResponseDTO>> getAllUnits(){
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> getUnitById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> putUnitById(@PathVariable Long id, @RequestBody UnitUpdateDTO data){
        return ResponseEntity.ok(unitService.putUnitById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long id){
        unitService.deleteUnitById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UnitResponseDTO> reactivateUnitById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.reactivateUnitById(id));
    }
}
