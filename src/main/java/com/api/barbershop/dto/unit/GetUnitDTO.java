package com.api.barbershop.dto.unit;

import com.api.barbershop.model.Unit;

public record GetUnitDTO(
        Long id,
        String phone,
        String address
) {
    public GetUnitDTO(Unit unit){
        this(unit.getId(), unit.getPhone(), unit.getAddress());
    }
}
