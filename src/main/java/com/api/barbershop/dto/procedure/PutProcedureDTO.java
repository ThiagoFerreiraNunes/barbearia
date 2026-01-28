package com.api.barbershop.dto.procedure;

public record PutProcedureDTO(
        String name,
        Double price,
        Integer estimatedTime
) {
}
