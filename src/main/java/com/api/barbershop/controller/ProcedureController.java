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
    public ResponseEntity<ProcedureResponseDTO> postProcedure(@RequestBody @Valid ProcedureCreateDTO data, UriComponentsBuilder builder){
        ProcedureResponseDTO procedure = procedureService.postProcedure(data);
        URI uri = builder.path("/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }

    @GetMapping
    public ResponseEntity<List<ProcedureResponseDTO>> getAllProcedures(){
        return ResponseEntity.ok(procedureService.getAllProcedures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> getProcedureById(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.getProcedureById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> putProcedureById(@PathVariable Long id, @RequestBody ProcedureUpdateDTO data){
        return ResponseEntity.ok(procedureService.putProcedureById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long id){
        procedureService.deleteProcedureById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProcedureResponseDTO> reactivateProcedureById(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.reactivateProcedureById(id));
    }
}
