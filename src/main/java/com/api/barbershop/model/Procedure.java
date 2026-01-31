package com.api.barbershop.model;

import com.api.barbershop.dto.procedure.ProcedureCreateDTO;
import com.api.barbershop.dto.procedure.ProcedureUpdateDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "tb_procedures")
@Entity(name = "Procedure")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "procedure_id")
    private Long id;

    @Column(name = "procedure_name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "estimated_time_minutes")
    private Integer estimatedTime;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Procedure(ProcedureCreateDTO data){
        this.name = data.name();
        this.price = data.price();
        this.estimatedTime = data.estimatedTime();
        this.isAvailable = true;
    }

    public void update(ProcedureUpdateDTO data){
        if(data.name() != null) this.name = data.name();
        if(data.price() != null) this.price = data.price();
        if(data.estimatedTime() != null) this.estimatedTime = data.estimatedTime();
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }
}
