package com.api.barbershop.dto.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record PostAppointmentDTO(
        @NotNull Long barberId,
        @NotNull Long clientId,
        @NotNull Long unitId,
        @NotEmpty List<Long> procedureIds,
        @NotNull @FutureOrPresent @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @NotNull @FutureOrPresent @JsonFormat(pattern = "HH:mm") LocalTime start
) {
}
