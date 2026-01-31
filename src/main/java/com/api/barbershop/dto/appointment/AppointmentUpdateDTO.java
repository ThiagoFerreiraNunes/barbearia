package com.api.barbershop.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PutAppointmentDTO(
        Long barberId,
        Long clientId,
        Long unitId,
        List<Long> procedureIds,
        @JsonFormat(pattern = "yyyy-MM-dd") @FutureOrPresent LocalDate date,
        @JsonFormat(pattern = "HH:mm") @FutureOrPresent LocalTime start
) {
}
