package com.api.barbershop.dto.barber;

import com.api.barbershop.model.Barber;

public record GetBarberSimpleDTO(
        Long id,
        String name,
        String cpf
) {
    public GetBarberSimpleDTO(Barber barber){
        this(barber.getId(), barber.getName(), barber.getCpf());
    }
}
