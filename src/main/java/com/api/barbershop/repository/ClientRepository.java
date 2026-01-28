package com.api.barbershop.repository;

import com.api.barbershop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.isAvailable = true ORDER BY c.name")
    List<Client> findAllByAvailableAndSortByName();
}
