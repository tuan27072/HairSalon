package com.example.demo.model.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StaffSlotResponse {
    List<LocalDate> staff;
}
