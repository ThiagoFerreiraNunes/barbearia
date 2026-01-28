package com.api.barbershop.model;

import com.api.barbershop.dto.procedure.PostProcedureDTO;
import com.api.barbershop.dto.procedure.PutProcedureDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Double price;

    @Column(name = "estimated_time_minutes")
    private Integer estimatedTime;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Procedure(PostProcedureDTO data){
        this.name = data.name();
        this.price = data.price();
        this.estimatedTime = data.estimatedTime();
    }

    public void update(PutProcedureDTO data){
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
