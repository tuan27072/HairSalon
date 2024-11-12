package com.example.demo.Entity;

import com.example.demo.Entity.Enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column(unique = true)
    String email;
    String password;
    String fullName;
    @Column(unique = true)
    String phone;

    @Enumerated(EnumType.STRING)
    Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }

    @ManyToMany(mappedBy = "accounts")
    List<Service> services;

    @OneToMany(mappedBy = "account")
            @JsonIgnore
    List<Order> orders;

    @ManyToOne
            @JoinColumn(name="store_id")
    Store store;

    @OneToMany(mappedBy ="account")
            @JsonIgnore
    List<StylistSlot>stylistSlots;
}
