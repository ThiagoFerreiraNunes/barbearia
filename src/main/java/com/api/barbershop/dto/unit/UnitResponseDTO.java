package com.api.barbershop.dto.unit;

import com.api.barbershop.model.Unit;

public record UnitResponseDTO(
        Long id,
        String phone,
        String address
) {
    public UnitResponseDTO(Unit unit){
        this(unit.getId(), unit.getPhone(), unit.getAddress());
    }
}
