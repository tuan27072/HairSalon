package com.example.demo.api;

import com.example.demo.Entity.Account;
import com.example.demo.Service.AccountService;
import com.example.demo.model.request.LoginRequest;
import com.example.demo.model.request.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@SecurityRequirement(name="api")
public class AccountAPI {
    @Autowired
    AccountService accountService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody RegisterRequest registerRequest){
        Account account= accountService.register(registerRequest);
        return ResponseEntity.ok(account);

    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest){
        Account account= accountService.login(loginRequest);
        return ResponseEntity.ok(account);
    }

}
