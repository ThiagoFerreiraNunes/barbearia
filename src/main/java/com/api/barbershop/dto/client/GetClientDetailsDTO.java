package com.api.barbershop.dto.client;

import com.api.barbershop.dto.plan.GetPlanDTO;
import com.api.barbershop.model.Client;

public record GetClientDetailsDTO(
        Long id,
        String name,
        String phone,
        String cpf,
        GetPlanDTO plan
) {
    public GetClientDetailsDTO(Client client){
        this(client.getId(), client.getName(), client.getPhone(), client.getCpf(), new GetPlanDTO(client.getPlan()));
    }
}
