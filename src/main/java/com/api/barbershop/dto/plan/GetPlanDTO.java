package com.api.barbershop.dto.plan;

import com.api.barbershop.model.Plan;

public record GetPlanDTO(Long id, String name, Double monthlyPrice) {
    public GetPlanDTO(Plan plan){
        this(plan.getId(), plan.getName(), plan.getMonthlyPrice());
    }
}
