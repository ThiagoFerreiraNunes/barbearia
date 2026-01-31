package com.api.barbershop.dto.appointment;

import com.api.barbershop.dto.barber.GetBarberSimpleDTO;
import com.api.barbershop.dto.client.GetClientSimpleDTO;
import com.api.barbershop.dto.unit.GetUnitDTO;
import com.api.barbershop.model.Appointment;
import com.api.barbershop.utils.FormatCurrency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record GetAppointmentDetailsDTO(
        Long id,
        GetBarberSimpleDTO barber,
        GetClientSimpleDTO client,
        GetUnitDTO unit,
        String totalPrice,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        @JsonFormat(pattern = "HH:mm") LocalTime start,
        @JsonFormat(pattern = "HH:mm") LocalTime end,
        List<GetAppointmentItem> items
) {
    public GetAppointmentDetailsDTO(Appointment appointment){
        this(
                appointment.getId(),
                new GetBarberSimpleDTO(appointment.getBarber()),
                new GetClientSimpleDTO(appointment.getClient()),
                new GetUnitDTO(appointment.getUnit()),
                FormatCurrency.format(appointment.getTotalPrice()),
                appointment.getDate(),
                appointment.getStart(),
                appointment.getEnd(),
                appointment.getItems().stream().map(GetAppointmentItem::new).toList()
        );
    }
}
