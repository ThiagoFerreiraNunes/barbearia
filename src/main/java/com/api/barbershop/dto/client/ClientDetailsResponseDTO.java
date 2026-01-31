package com.api.barbershop.dto.client;

import com.api.barbershop.model.Client;

public record ClientDetailsResponseDTO(
        Long id,
        String name,
        String phone,
        String cpf
) {
    public ClientDetailsResponseDTO(Client client){
        this(client.getId(), client.getName(), client.getPhone(), client.getCpf());
    }
}
