package com.api.barbershop.dto.procedure;

import com.api.barbershop.model.Procedure;
import com.api.barbershop.utils.FormatCurrency;

public record GetProcedureDTO(Long id, String name, String price, String estimatedTime) {
    public GetProcedureDTO(Procedure procedure){
        this(procedure.getId(), procedure.getName(), FormatCurrency.format(procedure.getPrice()), procedure.getEstimatedTime() + " minutos");
    }
}
