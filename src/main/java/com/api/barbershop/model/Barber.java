package com.api.barbershop.model;

import com.api.barbershop.dto.barber.BarberCreateDTO;
import com.api.barbershop.dto.barber.BarberUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tb_barbers")
@Entity(name = "Barber")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Barber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "barber_id")
    private Long id;

    @Column(name = "barber_name")
    private String name;

    @Column(name = "barber_phone")
    private String phone;

    @Column(name = "barber_cpf")
    private String cpf;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    public Barber(BarberCreateDTO data, Unit unit){
        this.name = data.name();
        this.phone = data.phone();
        this.cpf = data.cpf();
        this.unit = unit;
        this.isAvailable = true;
    }

    public void update(BarberUpdateDTO data, Unit unit){
        if(data.name() != null) this.name = data.name();
        if(data.phone() != null) this.phone = data.phone();
        if(data.cpf() != null) this.cpf = data.cpf();
        if(data.unitId() != null) this.unit = unit;
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }
}
