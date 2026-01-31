package com.api.barbershop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "tb_appointment_items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"appointment_id", "procedure_id"}))
@Entity(name = "AppointmentItem")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;

    @Column(name = "price")
    private BigDecimal price;

    public AppointmentItem(Appointment appointment, Procedure procedure){
        this.appointment = appointment;
        this.procedure = procedure;
        this.price = procedure.getPrice();
    }
}
