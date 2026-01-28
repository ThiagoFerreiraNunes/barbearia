package com.api.barbershop.controller;

import com.api.barbershop.dto.client.GetClientDetailsDTO;
import com.api.barbershop.dto.client.GetClientSimpleDTO;
import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
import com.api.barbershop.service.ClientService;
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
    public ResponseEntity<GetClientDetailsDTO> postClient(@RequestBody @Valid PostClientDTO data, UriComponentsBuilder builder){
        GetClientDetailsDTO client = clientService.postClient(data);
        URI uri = builder.path("/{id}").buildAndExpand(client.id()).toUri();
        return ResponseEntity.created(uri).body(client);
    }

    @GetMapping
    public ResponseEntity<List<GetClientSimpleDTO>> getAllClients(){
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClientDetailsDTO> getClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetClientDetailsDTO> putClientById(@PathVariable Long id, @RequestBody @Valid PutClientDTO data){
        return ResponseEntity.ok(clientService.putClientById(id, data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        clientService.deleteClientById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GetClientDetailsDTO> reactivateClientById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.reactivateClientById(id));
    }
}
