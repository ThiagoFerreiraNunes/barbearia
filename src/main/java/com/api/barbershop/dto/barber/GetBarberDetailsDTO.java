package com.api.barbershop.dto.barber;

import com.api.barbershop.dto.unit.GetUnitDTO;
import com.api.barbershop.model.Barber;

public record GetBarberDetailsDTO(Long id,
                                  String name,
                                  String phone,
                                  String cpf,
                                  GetUnitDTO unit
) {
    public GetBarberDetailsDTO(Barber barber){
        this(barber.getId(), barber.getName(), barber.getPhone(), barber.getCpf(), new GetUnitDTO(barber.getUnit()));
    }
}
