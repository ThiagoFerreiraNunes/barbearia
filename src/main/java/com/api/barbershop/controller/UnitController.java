package com.api.barbershop.controller;

import com.api.barbershop.dto.unit.GetUnitDTO;
import com.api.barbershop.dto.unit.PostUnitDTO;
import com.api.barbershop.dto.unit.PutUnitDTO;
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
    public ResponseEntity<GetUnitDTO> postUnit(@RequestBody @Valid PostUnitDTO data, UriComponentsBuilder builder){
        GetUnitDTO unit = unitService.postUnit(data);
        URI uri = builder.path("/{id}").buildAndExpand(unit.id()).toUri();
        return ResponseEntity.created(uri).body(unit);
    }

    @GetMapping
    public ResponseEntity<List<GetUnitDTO>> getAllUnits(){
        return ResponseEntity.ok(unitService.getAllUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUnitDTO> getUnitById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.getUnitById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUnitDTO> putUnitById(@PathVariable Long id, @RequestBody PutUnitDTO data){
        return ResponseEntity.ok(unitService.putUnitById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long id){
        unitService.deleteUnitById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetUnitDTO> reactivateUnitById(@PathVariable Long id){
        return ResponseEntity.ok(unitService.reactivateUnitById(id));
    }
}
