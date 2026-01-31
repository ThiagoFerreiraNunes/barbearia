package com.api.barbershop.repository;

import com.api.barbershop.model.Client;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.isAvailable = true ORDER BY c.name")
    List<Client> findAllByAvailableAndSortByName();

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    boolean existsByCpfAndIdNot(String cpf, Long id);

    boolean existsByPhoneAndIdNot(String phone, Long id);
}
