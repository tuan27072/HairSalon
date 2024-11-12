package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class ServiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    boolean isDeleted = false;
    float price;
    String image;
    @ManyToOne
    @JoinColumn(name = "service_id")
    Service service;

    @OneToMany(mappedBy = "serviceOption")
    @JsonIgnore
    List<OrderDetail> orderDetails;
}
