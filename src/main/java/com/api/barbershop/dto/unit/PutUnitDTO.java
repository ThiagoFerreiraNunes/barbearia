package com.api.barbershop.dto.unit;

import jakarta.validation.constraints.Size;

public record PutUnitDTO(
        @Size(min = 11, max = 11) String phone,
        @Size(max = 200) String address
) {
}
