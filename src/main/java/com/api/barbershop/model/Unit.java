package com.api.barbershop.model;

import com.api.barbershop.dto.unit.PostUnitDTO;
import com.api.barbershop.dto.unit.PutUnitDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "tb_units")
@Entity(name = "Unit")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "unit_phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Unit(PostUnitDTO data){
        this.phone = data.phone();
        this.address = data.address();
        this.isAvailable = true;
    }

    public void update(PutUnitDTO data){
        if(data.phone() != null) this.phone = data.phone();
        if(data.address() != null) this.address = data.address();
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }

}
