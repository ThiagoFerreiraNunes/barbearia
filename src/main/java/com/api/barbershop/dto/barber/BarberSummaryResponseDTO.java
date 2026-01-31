package com.api.barbershop.dto.barber;

import com.api.barbershop.model.Barber;

public record BarberSummaryResponseDTO(
        Long id,
        String name,
        String cpf
) {
    public BarberSummaryResponseDTO(Barber barber){
        this(barber.getId(), barber.getName(), barber.getCpf());
    }
}
