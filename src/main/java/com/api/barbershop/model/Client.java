package com.api.barbershop.model;

import com.api.barbershop.dto.client.PostClientDTO;
import com.api.barbershop.dto.client.PutClientDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tb_clients")
@Entity(name = "Client")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Column(name = "client_phone")
    private String phone;

    @Column(name = "client_cpf")
    private String cpf;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public Client(PostClientDTO data, Plan plan){
        this.name = data.name();
        this.phone = data.phone();
        this.cpf = data.cpf();
        this.plan = plan;
        this.isAvailable = true;
    }

    public void update(PutClientDTO data, Plan plan){
        if(data.name() != null) this.name = data.name();
        if(data.phone() != null) this.phone = data.phone();
        if(data.cpf() != null) this.cpf = data.cpf();
        if(data.planId() != null) this.plan = plan;
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }

}
