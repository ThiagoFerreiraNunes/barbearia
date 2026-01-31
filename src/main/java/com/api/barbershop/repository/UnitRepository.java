package com.api.barbershop.repository;

import com.api.barbershop.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    @Query("SELECT u FROM Unit u WHERE u.isAvailable = true")
    List<Unit> findAllByAvailable();

    boolean existsByPhone(String phone);

    boolean existsByAddress(String address);

    boolean existsByPhoneAndIdNot(String phone);

    boolean existsByAddressAndIdNot(String address);
}
