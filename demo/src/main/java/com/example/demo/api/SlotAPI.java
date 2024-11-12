package com.example.demo.api;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Slot;
import com.example.demo.Entity.Store;
import com.example.demo.Entity.StylistSlot;
import com.example.demo.Service.SlotService;
import com.example.demo.model.request.RegisterSlotForStaff;
import com.example.demo.model.response.StaffSlotResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SecurityRequirement(name="api")
@RestController
public class SlotAPI {
    @Autowired
    SlotService slotService;
    @GetMapping(value = "/api/slot")
    public ResponseEntity getAllStore(@RequestParam LocalDate date){
        List<Slot> slots= slotService.getListSlotAvailable(date);
        return ResponseEntity.ok(slots);
    }

    @GetMapping(value = "/api/available-staff")
    public ResponseEntity getAvailableStaff(@RequestParam LocalDate date, @RequestParam long slotId, @RequestParam long skillId){
        List<Account> available= slotService.getStaffAvailable(date, slotId, skillId);
        return ResponseEntity.ok(available);
    }

    @GetMapping(value = "/api/slot/staff")
    public ResponseEntity getAllStoreByStaff(@RequestParam int month, @RequestParam int year){
        StaffSlotResponse staffSlotResponse = slotService.getSlotStaff(month, year);
        return ResponseEntity.ok(staffSlotResponse);
    }

    @GetMapping(value = "/api/slot/manager")
    public ResponseEntity getAllStoreByManager(@RequestParam int month, @RequestParam int year){
        Map<LocalDate, List<Account>> slots= slotService.getSlotManager(month, year);
        return ResponseEntity.ok(slots);
    }

    @PostMapping(value="/api/slot/{staffId}")
    public ResponseEntity registerSlotByStaff(RegisterSlotForStaff registerSlotForStaff, @PathVariable Long staffId){
        List<StylistSlot> newStylistSlot= slotService.registerSlotByStaff(registerSlotForStaff, staffId);
        return ResponseEntity.ok(newStylistSlot);

    }
}
