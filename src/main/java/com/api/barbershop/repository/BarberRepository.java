package com.api.barbershop.repository;

import com.api.barbershop.model.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BarberRepository extends JpaRepository<Barber, Long> {
    @Query("SELECT b FROM Barber b WHERE b.isAvailable = true ORDER BY b.name")
    List<Barber> findAllByAvailableAndSortByName();
}
