package com.api.barbershop.dto.appointment;

import com.api.barbershop.model.AppointmentItem;
import com.api.barbershop.utils.FormatCurrency;

public record GetAppointmentItem(
        Long id,
        String procedure,
        String price
) {
    public GetAppointmentItem(AppointmentItem item){
        this(item.getId(), item.getProcedure().getName(), FormatCurrency.format(item.getPrice()));
    }
}
