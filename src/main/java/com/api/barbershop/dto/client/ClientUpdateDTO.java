package com.api.barbershop.dto.client;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClientUpdateDTO(
        @Size(max = 100) String name,
        @Size(min = 11, max = 11) String phone,
        @Size(min = 11, max = 11) @CPF String cpf
) {
}
