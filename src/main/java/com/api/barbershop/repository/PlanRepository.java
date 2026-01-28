package com.api.barbershop.repository;

import com.api.barbershop.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Query("SELECT p FROM Plan p WHERE p.isAvailable = true ORDER BY p.monthlyPrice")
    List<Plan> findAllByAvailableAndSortByPrice();
}
