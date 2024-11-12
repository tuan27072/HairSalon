package com.example.demo.api;

import com.example.demo.Entity.Account;
import com.example.demo.Service.AccountService;
import com.example.demo.model.request.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="api")
@RestController
public class ManagerAPI {
    @Autowired
    AccountService accountService;
    @GetMapping(value = "/api/manager/{storeId}")
    public ResponseEntity getAllManger(@PathVariable long storeId){
        List<Account> managers= accountService.getStaffByStoreId(storeId);
        return ResponseEntity.ok(managers);
    }

    @PostMapping(value="/api/manager/{storeId}")
    public ResponseEntity createNewStaff(@RequestBody RegisterRequest registerRequest, @PathVariable long storeId){
        Account newManager =accountService.createManager(registerRequest, storeId  );
        return ResponseEntity.ok(newManager);
    }
}
