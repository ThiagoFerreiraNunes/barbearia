package com.api.barbershop.model;

import com.api.barbershop.dto.client.ClientCreateDTO;
import com.api.barbershop.dto.client.ClientUpdateDTO;
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

    public Client(ClientCreateDTO data){
        this.name = data.name();
        this.phone = data.phone();
        this.cpf = data.cpf();
        this.isAvailable = true;
    }

    public void update(ClientUpdateDTO data){
        if(data.name() != null) this.name = data.name();
        if(data.phone() != null) this.phone = data.phone();
        if(data.cpf() != null) this.cpf = data.cpf();
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }

}
