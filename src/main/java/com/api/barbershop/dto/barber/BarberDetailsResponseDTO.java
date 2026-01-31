package com.api.barbershop.dto.barber;

import com.api.barbershop.dto.unit.UnitResponseDTO;
import com.api.barbershop.model.Barber;

public record BarberDetailsResponseDTO(
        Long id,
        String name,
        String phone,
        String cpf,
        UnitResponseDTO unit
) {
    public BarberDetailsResponseDTO(Barber barber){
        this(barber.getId(), barber.getName(), barber.getPhone(), barber.getCpf(), new UnitResponseDTO(barber.getUnit()));
    }
}
