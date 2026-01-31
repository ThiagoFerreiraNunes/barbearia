package com.api.barbershop.dto.appointment;

import com.api.barbershop.dto.barber.BarberSummaryResponseDTO;
import com.api.barbershop.dto.client.ClientSummaryResponseDTO;
import com.api.barbershop.dto.unit.UnitResponseDTO;
import com.api.barbershop.model.Appointment;
import com.api.barbershop.utils.FormatCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AppointmentDetailsResponseDTO(
        Long id,
        BarberSummaryResponseDTO barber,
        ClientSummaryResponseDTO client,
        UnitResponseDTO unit,
        String totalPrice,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime start,
        @JsonFormat(pattern = "HH:mm") LocalTime end,
        List<AppointmentItemResponseDTO> items
) {
    public AppointmentDetailsResponseDTO(Appointment appointment){
        this(
                appointment.getId(),
                new BarberSummaryResponseDTO(appointment.getBarber()),
                new ClientSummaryResponseDTO(appointment.getClient()),
                new UnitResponseDTO(appointment.getUnit()),
                FormatCurrency.format(appointment.getTotalPrice()),
                appointment.getDate(),
                appointment.getStart(),
                appointment.getEnd(),
                appointment.getItems().stream().map(AppointmentItemResponseDTO::new).toList()
        );
    }
}
