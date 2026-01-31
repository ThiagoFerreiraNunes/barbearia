package com.api.barbershop.dto.procedure;

import com.api.barbershop.model.Procedure;
import com.api.barbershop.utils.FormatCurrency;

public record ProcedureResponseDTO(
        Long id,
        String name,
        String price,
        String estimatedTime
) {
    public ProcedureResponseDTO(Procedure procedure){
        this(procedure.getId(), procedure.getName(), FormatCurrency.format(procedure.getPrice()), procedure.getEstimatedTime() + " minutos");
    }
}
