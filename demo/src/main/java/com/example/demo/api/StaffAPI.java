package com.example.demo.api;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Store;
import com.example.demo.Service.AccountService;
import com.example.demo.model.request.RegisterRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name="api")
@RestController
public class StaffAPI {
    @Autowired
    AccountService accountService;
    @GetMapping(value = "/api/staff/{storeId}")
    public ResponseEntity getAllStaff(@PathVariable long storeId){
        List<Account> staffs= accountService.getStaffByStoreId(storeId);
        return ResponseEntity.ok(staffs);
    }

    @PostMapping(value="/api/staff/{storeId}")
    public ResponseEntity createNewStaff(@RequestBody RegisterRequest registerRequest, @PathVariable long storeId){
        Account newStaff =accountService.createStaff(registerRequest, storeId  );
        return ResponseEntity.ok(newStaff);
    }

}
