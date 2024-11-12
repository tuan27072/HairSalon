package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class StylistSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    LocalDate date;


    @ManyToOne
        @JoinColumn(name="slot_id")
    Slot slot;
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @ManyToOne
        @JoinColumn(name = "orderDetail_id")
    @JsonIgnore
    OrderDetail orderDetail;


}
