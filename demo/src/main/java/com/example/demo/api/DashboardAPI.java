package com.example.demo.api;

import com.example.demo.Entity.Account;
import com.example.demo.Service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SecurityRequirement(name="api")
@RestController
@RequestMapping("api/dashboard")
public class DashboardAPI {

    @Autowired
    DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity getAllManger(){
        return ResponseEntity.ok(dashboardService.getTotal());
    }
}
