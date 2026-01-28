package com.api.barbershop.dto.client;

import org.hibernate.validator.constraints.br.CPF;

public record PutClientDTO(
        String name,
        String phone,
        @CPF String cpf,
        Long planId
) {
}
