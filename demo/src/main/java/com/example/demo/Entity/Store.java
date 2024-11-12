package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    String address;

    String phone;

    boolean isDeleted=false;

    @OneToMany(mappedBy = "store")
            @JsonIgnore
    List<Account>accounts;
    @OneToMany(mappedBy = "store")
            @JsonIgnore
    List<Order> orders;

}
