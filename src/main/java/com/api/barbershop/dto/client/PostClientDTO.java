package com.api.barbershop.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record PostClientDTO(
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank @CPF String cpf,
        @NotNull Long planId
) {
}
