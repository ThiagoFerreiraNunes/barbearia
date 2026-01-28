package com.api.barbershop.dto.client;

import com.api.barbershop.model.Client;

public record GetClientSimpleDTO(
        Long id,
        String name,
        String cpf
) {
    public GetClientSimpleDTO(Client client){
        this(client.getId(), client.getName(), client.getCpf());
    }
}
