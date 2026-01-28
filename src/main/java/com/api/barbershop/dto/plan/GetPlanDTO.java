package com.api.barbershop.dto.plan;

import com.api.barbershop.model.Plan;
import com.api.barbershop.utils.FormatCurrency;

public record GetPlanDTO(Long id, String name, String monthlyPrice) {
    public GetPlanDTO(Plan plan){
        this(plan.getId(), plan.getName(), FormatCurrency.format(plan.getMonthlyPrice()));
    }
}
