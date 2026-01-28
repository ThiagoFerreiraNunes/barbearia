package com.api.barbershop.dto.plan;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostPlanDTO(@NotBlank String name, @NotNull Double monthlyPrice) {
}
