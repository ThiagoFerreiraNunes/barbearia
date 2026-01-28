package com.api.barbershop.model;

import com.api.barbershop.dto.plan.PostPlanDTO;
import com.api.barbershop.dto.plan.PutPlanDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tb_plans")
@Entity(name = "Plan")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_name")
    private String name;

    @Column(name = "monthly_price")
    private Double monthlyPrice;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Plan(PostPlanDTO data){
        this.name = data.name();
        this.monthlyPrice = data.monthlyPrice();
        this.isAvailable = true;
    }

    public void update(PutPlanDTO data){
        if(data.name() != null) this.name = data.name();
        if(data.monthlyPrice() != null) this.monthlyPrice = data.monthlyPrice();
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }
}
