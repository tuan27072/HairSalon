package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {
    Optional<Slot> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
    Slot findSlotById(long slotId);

}
