package com.example.demo.api;

import com.example.demo.Entity.Service;
import com.example.demo.Entity.Store;
import com.example.demo.Service.ServiceService;
import com.example.demo.Service.StoreService;
import com.example.demo.model.request.ServiceRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.List;
@SecurityRequirement(name="api")
@RestController
public class ServiceAPI {
    @Autowired
    ServiceService serviceService;
    //GET
    @GetMapping(value = "/api/service")
    public ResponseEntity getAllService(){
        List<Service> services= serviceService.getAllService();
        return ResponseEntity.ok(services);
    }

    @PostMapping(value = "/api/service")
    public ResponseEntity createNewService(@RequestBody ServiceRequest serviceRequest){
        Service newservice= serviceService.createNewService(serviceRequest);
        return ResponseEntity.ok(newservice);
    }
    @DeleteMapping(value = "/api/service/{id}")
    public ResponseEntity deleteService(@PathVariable long id){
        Service service= serviceService.deleteService(id);
        return ResponseEntity.ok(service);

    }
}
