package com.api.barbershop.repository;

import com.api.barbershop.model.Procedure;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    @Query("SELECT p FROM Procedure p WHERE p.isAvailable = true ORDER BY p.name")
    List<Procedure> findAllByAvailableAndSortByName();

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
