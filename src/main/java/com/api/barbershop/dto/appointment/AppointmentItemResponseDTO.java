package com.api.barbershop.dto.appointment;

import com.api.barbershop.model.AppointmentItem;
import com.api.barbershop.utils.FormatCurrency;

public record AppointmentItemResponseDTO(
        Long id,
        String procedure,
        String price
) {
    public AppointmentItemResponseDTO(AppointmentItem item){
        this(item.getId(), item.getProcedure().getName(), FormatCurrency.format(item.getPrice()));
    }
}
