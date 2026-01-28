package com.api.barbershop.dto.barber;

import org.hibernate.validator.constraints.br.CPF;

public record PutBarberDTO(String name,
                           String phone,
                           @CPF String cpf,
                           Long unitId) {
}
