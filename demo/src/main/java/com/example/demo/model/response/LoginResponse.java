package com.example.demo.model.response;

import com.example.demo.Entity.Account;
import lombok.Data;

@Data
public class LoginResponse extends Account {
    String token;
}
