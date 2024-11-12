package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    String description;
    String image;

    boolean isDeleted=false;
    @ManyToOne
            @JoinColumn(name = "category_id")
    Category category;

    @OneToMany(mappedBy = "service")
            @JsonIgnore
    List<ServiceOption> serviceOptions;

    @ManyToMany
            @JoinTable(
                    name="account_service",
                    joinColumns = @JoinColumn(name="service_id"),
                    inverseJoinColumns = @JoinColumn(name="account_id")

            )
            @JsonIgnore
    List<Account> accounts = new ArrayList<>();

}
