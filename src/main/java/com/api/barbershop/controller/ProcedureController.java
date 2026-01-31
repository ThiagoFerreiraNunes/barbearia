package com.api.barbershop.controller;

import com.api.barbershop.dto.procedure.ProcedureResponseDTO;
import com.api.barbershop.dto.procedure.ProcedureCreateDTO;
import com.api.barbershop.dto.procedure.ProcedureUpdateDTO;
import com.api.barbershop.service.procedure.ProcedureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/procedures")
public class ProcedureController {
    @Autowired ProcedureService procedureService;

    @PostMapping
    public ResponseEntity<ProcedureResponseDTO> create(@RequestBody @Valid ProcedureCreateDTO data, UriComponentsBuilder builder){
        ProcedureResponseDTO procedure = procedureService.create(data);
        URI uri = builder.path("/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }

    @GetMapping
    public ResponseEntity<List<ProcedureResponseDTO>> findAll(){
        return ResponseEntity.ok(procedureService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> update(@PathVariable Long id, @RequestBody ProcedureUpdateDTO data){
        return ResponseEntity.ok(procedureService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        procedureService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> reactivate(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.reactivate(id));
    }
}
