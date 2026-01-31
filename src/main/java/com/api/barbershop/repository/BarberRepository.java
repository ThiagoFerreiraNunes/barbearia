package com.api.barbershop.repository;

import com.api.barbershop.model.Barber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BarberRepository extends JpaRepository<Barber, Long> {
    @Query("SELECT b FROM Barber b WHERE b.isAvailable = true ORDER BY b.name")
    List<Barber> findAllByAvailableAndSortByName();

    @Query("""
            SELECT b FROM Barber b
            JOIN FETCH b.unit
            WHERE b.id = :id
            """)
    Optional<Barber> findByIdWithDetails(@Param("id") Long id);

    boolean existsByPhone(String phone);

    boolean existsByCpf(String cpf);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByCpfAndIdNot(String cpf, Long id);
}
