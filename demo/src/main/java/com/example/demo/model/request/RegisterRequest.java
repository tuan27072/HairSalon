package com.example.demo.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    String email;
    String password;
    String fullName;
    String phone;
    String role;
    List<Long> serviceIds;
}
