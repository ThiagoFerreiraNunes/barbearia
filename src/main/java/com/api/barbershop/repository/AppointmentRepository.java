package com.api.barbershop.repository;

import com.api.barbershop.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("""
            SELECT a FROM Appointment a
            JOIN FETCH a.barber
            JOIN FETCH a.client
            JOIN FETCH a.unit
            WHERE a.isAvailable = true
            ORDER BY a.date DESC, a.start DESC
            """)
    List<Appointment> findAllByAvailableAndSortByDate();

    @Query("""
            SELECT a FROM Appointment a
            JOIN FETCH a.barber
            JOIN FETCH a.client
            JOIN FETCH a.unit
            JOIN FETCH a.items i
            JOIN FETCH i.procedure
            WHERE a.id = :id
            """)
    Optional<Appointment> findByIdWithDetails(@Param("id") Long id);
}
