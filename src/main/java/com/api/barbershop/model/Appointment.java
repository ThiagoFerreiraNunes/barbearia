package com.api.barbershop.model;

import com.api.barbershop.dto.appointment.PostAppointmentDTO;
import com.api.barbershop.dto.appointment.PutAppointmentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_appointments")
@Entity(name = "Appointment")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "appointment_date")
    private LocalDate date;

    @Column(name = "start_time")
    private LocalTime start;

    @Column(name = "end_time")
    private LocalTime end;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barber_id")
    private Barber barber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentItem> items = new ArrayList<>();

    public Appointment(PostAppointmentDTO data, Barber barber, Client client, Unit unit, List<Procedure> procedures){
        this.date = data.date();
        this.start = data.start();
        this.isAvailable = true;
        this.barber = barber;
        this.client = client;
        this.unit = unit;

        this.totalPrice = BigDecimal.ZERO;
        this.items = new ArrayList<>();
        int totalMinutes = 0;

        for(Procedure proc : procedures){
            AppointmentItem item = new AppointmentItem(this, proc);
            this.items.add(item);
            this.totalPrice = this.totalPrice.add(item.getPrice());

            totalMinutes += proc.getEstimatedTime();
        }

        this.end = this.start.plusMinutes(totalMinutes);
    }

    public void update(PutAppointmentDTO data, Barber barber, Client client, Unit unit, List<Procedure> procedures){
        boolean recalculate = false;

        if(data.date() != null) this.date = data.date();
        if(data.start() != null){
            this.start = data.start();
            recalculate = true;
        }
        if(data.barberId() != null) this.barber = barber;
        if(data.clientId() != null) this.client = client;
        if(data.unitId() != null) this.unit = unit;
        if(data.procedureIds() != null && !data.procedureIds().isEmpty()){
            this.totalPrice = BigDecimal.ZERO;
            this.items.clear();
            int totalMinutes = 0;

            for(Procedure proc : procedures){
                AppointmentItem item = new AppointmentItem(this, proc);
                this.items.add(item);
                this.totalPrice = this.totalPrice.add(item.getPrice());

                totalMinutes += proc.getEstimatedTime();
            }

            recalculate = true;
            this.end = this.start.plusMinutes(totalMinutes);
        }

        if(recalculate && data.procedureIds() == null){
            int totalMinutes = this.items.stream()
                    .mapToInt(i -> i.getProcedure().getEstimatedTime())
                    .sum();

            this.end = this.start.plusMinutes(totalMinutes);
        }
    }

    public void delete(){
        this.isAvailable = false;
    }

    public void reactivate(){
        this.isAvailable = true;
    }
}
