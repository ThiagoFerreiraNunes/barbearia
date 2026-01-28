package com.api.barbershop.dto.client;

import com.api.barbershop.model.Client;

public record GetClientDetailsDTO(
        Long id,
        String name,
        String phone,
        String cpf,
        String plan
) {
    public GetClientDetailsDTO(Client client){
        this(client.getId(), client.getName(), client.getPhone(), client.getCpf(), client.getPlan().getName());
    }
}
