package com.example.demo.Service;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Slot;
import com.example.demo.Entity.StylistSlot;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.RegisterSlotForStaff;
import com.example.demo.model.response.StaffSlotResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.SlotRepository;
import com.example.demo.repository.StylistSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
public class SlotService {
    @Autowired
    SlotRepository slotRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    StylistSlotRepository stylistSlotRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ServiceRepository serviceRepository;

    public Slot createSlotIfNotExists(LocalTime startTime, LocalTime endTime, String label) {
        // Check if slot exists by start and end time
        Optional<Slot> existingSlot = slotRepository.findByStartTimeAndEndTime(startTime, endTime);

        // If the slot does not exist, create it
        if (existingSlot.isEmpty()) {
            Slot slot = new Slot();
            slot.setStartTime(startTime);
            slot.setEndTime(endTime);
            slot.setLabel(label);
            return slotRepository.save(slot);
        }

        // If the slot exists, return it or handle accordingly
        return existingSlot.get();
    }
    public List<Slot> getAllSlot(){
        return slotRepository.findAll();
    }

    public List<Slot> getListSlotAvailable(LocalDate date){
        List<Slot> slots =  slotRepository.findAll();

        for(Slot slot: slots){
            // check if slot have available staff => slot available
            boolean isAvailable = !stylistSlotRepository.findStylistSlotByDateAndSlotAndOrderDetailNull(date, slot).isEmpty();
            slot.setAvailable(isAvailable);
        }

        return slots;
    }

    public List<Account> getStaffAvailable(LocalDate date, long slotId, long serviceId){
        Slot slot = slotRepository.findSlotById(slotId);
        com.example.demo.Entity.Service service = serviceRepository.findServiceById(serviceId);
        List<StylistSlot> stylistSlots = stylistSlotRepository.findStylistSlotByDateAndSlotAndAccountServicesContainingAndOrderDetailNull(date, slot, service);
        List<Account> staffAvailable = new ArrayList<>();

        for(StylistSlot stylistSlot: stylistSlots){
            if(!staffAvailable.contains(stylistSlot.getAccount())){
                staffAvailable.add(stylistSlot.getAccount());
            }
        }
        return staffAvailable;
    }

    public StaffSlotResponse getSlotStaff(int month, int year){
        StaffSlotResponse staffSlotResponse = new StaffSlotResponse();
        Account account = accountService.getCurrentAccount();
        List<StylistSlot> list = stylistSlotRepository.findStylistSlotByDate(month, year, account.getId());
        List<LocalDate> dates = new ArrayList<>();

        for(StylistSlot stylistSlot: list){
            if(!dates.contains(stylistSlot.getDate())){
               dates.add(stylistSlot.getDate());
            }
        }

        staffSlotResponse.setStaff(dates.stream().sorted().toList());

        return staffSlotResponse;
    }

    public Map<LocalDate, List<Account>> getSlotManager(int month, int year){
        Account account = accountService.getCurrentAccount();
        List<StylistSlot> list = stylistSlotRepository.findStylistSlotByDateManager(month, year, account.getStore().getId());
        Map<LocalDate, List<Account>> response = new HashMap<>();

        for(StylistSlot stylistSlot: list){
            if(response.keySet().contains(stylistSlot.getDate())){

                List<Account> accounts = response.get(stylistSlot.getDate());

                if(!accounts.contains(stylistSlot.getAccount())){
                    accounts.add(stylistSlot.getAccount());
                }

            }else{
                List<Account> accounts = new ArrayList<>();
                accounts.add(stylistSlot.getAccount());
                response.put(stylistSlot.getDate(), accounts);
            }
        }

        return response;
    }



    public List<StylistSlot> registerSlotByStaff(RegisterSlotForStaff registerSlotForStaff, Long staffId){
        Account staff = accountRepository.findAccountById(staffId);
        List<StylistSlot> stylistSlots= new ArrayList<>();
        List<StylistSlot> existingSlots = stylistSlotRepository.findStylistSlotByAccountAndDate(staff,registerSlotForStaff.getDate());

        if (!existingSlots.isEmpty()) {
            throw new BadRequestException("Staff has already registered for this day's slot");
        }
        for(Slot slot: slotRepository.findAll()){
            StylistSlot stylistSlot = new StylistSlot();
            stylistSlot.setSlot(slot);
            stylistSlot.setDate(registerSlotForStaff.getDate());
            stylistSlot.setAccount(staff);
            stylistSlots.add(stylistSlot);
        }
        return stylistSlotRepository.saveAll(stylistSlots);
    }
}
