package com.api.barbershop.dto.unit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitCreateDTO(
        @NotBlank @Size(min = 11, max = 11) String phone,
        @NotBlank @Size(max = 200) String address
) {
}
