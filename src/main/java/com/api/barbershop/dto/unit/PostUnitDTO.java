package com.api.barbershop.dto.unit;

import jakarta.validation.constraints.NotBlank;

public record PostUnitDTO(
        @NotBlank String phone,
        @NotBlank String address
) {
}
