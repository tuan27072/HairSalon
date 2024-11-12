package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    private boolean isAvailable = false;

    @OneToMany(mappedBy = "slot")
    @JsonIgnore
    List<StylistSlot> stylistSlots;

}
