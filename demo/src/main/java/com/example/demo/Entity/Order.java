package com.example.demo.Entity;

import com.example.demo.Entity.Enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    float total;
    LocalDate date;

    int rate;
    String feedback;

    @Enumerated(EnumType.STRING)
    OrderStatus status = OrderStatus.INIT;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
    @ManyToOne
    @JoinColumn(name = "store_id")
    Store store;
}
