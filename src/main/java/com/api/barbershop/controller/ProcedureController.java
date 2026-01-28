package com.api.barbershop.controller;

import com.api.barbershop.dto.procedure.GetProcedureDTO;
import com.api.barbershop.dto.procedure.PostProcedureDTO;
import com.api.barbershop.dto.procedure.PutProcedureDTO;
import com.api.barbershop.service.ProcedureService;
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
    public ResponseEntity<GetProcedureDTO> postProcedure(@RequestBody @Valid PostProcedureDTO data, UriComponentsBuilder builder){
        GetProcedureDTO procedure = procedureService.postProcedure(data);
        URI uri = builder.path("/{id}").buildAndExpand(procedure.id()).toUri();
        return ResponseEntity.created(uri).body(procedure);
    }

    @GetMapping
    public ResponseEntity<List<GetProcedureDTO>> getAllProcedures(){
        return ResponseEntity.ok(procedureService.getAllProcedures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProcedureDTO> getProcedureById(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.getProcedureById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetProcedureDTO> putProcedureById(@PathVariable Long id, @RequestBody PutProcedureDTO data){
        return ResponseEntity.ok(procedureService.putProcedureById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUnitById(@PathVariable Long id){
        procedureService.deleteProcedureById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetProcedureDTO> reactivateProcedureById(@PathVariable Long id){
        return ResponseEntity.ok(procedureService.reactivateProcedureById(id));
    }
}
