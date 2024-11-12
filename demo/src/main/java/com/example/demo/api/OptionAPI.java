package com.example.demo.api;

import com.example.demo.Entity.ServiceOption;
import com.example.demo.Entity.Store;
import com.example.demo.Service.OptionService;
import com.example.demo.model.request.OptionRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name="api")
@RestController
public class OptionAPI {
    @Autowired
    OptionService optionService;

    @GetMapping(value = "/api/option")
    public ResponseEntity getAllOption(){
        List<ServiceOption> options= optionService.getAllOption();
        return ResponseEntity.ok(options);
    }

    @PostMapping(value="/api/option")
    public ResponseEntity createNewOption(@RequestBody OptionRequest optionRequest){
        ServiceOption newoption =optionService.createNewOption(optionRequest);

        return ResponseEntity.ok(newoption);
    }
    @DeleteMapping(value = "/api/option/{id}")
    public ResponseEntity deleteOption(@PathVariable long id){
        ServiceOption option = optionService.deleteOption(id);
        return ResponseEntity.ok(option);

    }
}
