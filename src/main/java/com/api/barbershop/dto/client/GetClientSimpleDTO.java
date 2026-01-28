package com.api.barbershop.dto.client;

import com.api.barbershop.model.Client;

public record GetClientSimpleDTO(
        Long id,
        String name,
        String cpf,
        String plan
) {
    public GetClientSimpleDTO(Client client){
        this(client.getId(), client.getName(), client.getCpf(), client.getPlan().getName());
    }
}
