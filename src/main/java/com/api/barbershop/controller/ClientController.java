package com.api.barbershop.controller;

import com.api.barbershop.dto.client.ClientDetailsResponseDTO;
import com.api.barbershop.dto.client.ClientSummaryResponseDTO;
import com.api.barbershop.dto.client.ClientCreateDTO;
import com.api.barbershop.dto.client.ClientUpdateDTO;
import com.api.barbershop.service.client.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDetailsResponseDTO> update(@RequestBody @Valid ClientCreateDTO data, UriComponentsBuilder builder){
        ClientDetailsResponseDTO client = clientService.update(data);
        URI uri = builder.path("/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientSummaryResponseDTO>> findAll(){
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ClientUpdateDTO data){
        return ResponseEntity.ok(clientService.update(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> reactivate(@PathVariable Long id){
        return ResponseEntity.ok(clientService.reactivate(id));
    }
}
