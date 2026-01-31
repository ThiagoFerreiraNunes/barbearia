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
    public ResponseEntity<ClientDetailsResponseDTO> postClient(@RequestBody @Valid ClientCreateDTO data, UriComponentsBuilder builder){
        ClientDetailsResponseDTO client = clientService.postClient(data);
        URI uri = builder.path("/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping
    public ResponseEntity<List<ClientSummaryResponseDTO>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> getClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> putClientById(@PathVariable Long id, @RequestBody @Valid ClientUpdateDTO data){
        return ResponseEntity.ok(clientService.putClientById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDetailsResponseDTO> reactivateClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.reactivateClientById(id));
    }
}
