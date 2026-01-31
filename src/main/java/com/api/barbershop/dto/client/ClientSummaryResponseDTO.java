package com.api.barbershop.dto.client;

import com.api.barbershop.model.Client;

public record ClientSummaryResponseDTO(
        Long id,
        String name,
        String cpf
) {
    public ClientSummaryResponseDTO(Client client){
        this(client.getId(), client.getName(), client.getCpf());
    }
}
