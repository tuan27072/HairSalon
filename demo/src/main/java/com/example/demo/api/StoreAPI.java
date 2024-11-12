package com.example.demo.api;

import com.example.demo.Entity.Store;
import com.example.demo.Service.StoreService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name="api")
@RestController
public class StoreAPI {
    @Autowired
    StoreService storeService;
    //GET
    @GetMapping(value = "/api/store")
    public ResponseEntity getAllStore(){
        List<Store> stores= storeService.getAllStore();
        return ResponseEntity.ok(stores);
    }
    //POST
    @PostMapping(value="/api/store")
    public ResponseEntity createNewStore(@RequestBody Store store){
        Store newStore =storeService.createNewStore(store);
        return ResponseEntity.ok(newStore);

    }

    //PUT
    //DELETE
    @DeleteMapping(value = "/api/store/{id}")
    public ResponseEntity deleteStore(@PathVariable long id){
        Store store= storeService.deleteStore(id);
        return ResponseEntity.ok(store);

    }
}
