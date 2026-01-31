package com.api.barbershop.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PostClientDTO(
        @NotBlank @Size(max = 100) String name,
        @NotBlank @Size(min = 11, max = 11) String phone,
        @NotBlank @Size(min = 11, max = 11) @CPF String cpf
) {
}
