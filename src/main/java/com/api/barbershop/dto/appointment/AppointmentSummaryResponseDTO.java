package com.api.barbershop.dto.appointment;

import com.api.barbershop.model.Appointment;
import com.api.barbershop.utils.FormatCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentSummaryResponseDTO(
        Long id,
        String barber,
        String client,
        String unit,
        String totalPrice,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime start,
        @JsonFormat(pattern = "HH:mm") LocalTime end
) {
    public AppointmentSummaryResponseDTO(Appointment appointment){
        this(
                appointment.getId(),
                appointment.getBarber().getName(),
                appointment.getClient().getName(),
                appointment.getUnit().getAddress(),
                FormatCurrency.format(appointment.getTotalPrice()),
                appointment.getDate(),
                appointment.getStart(),
                appointment.getEnd()
        );
    }
}
