package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Service;
import com.example.demo.Entity.Slot;
import com.example.demo.Entity.StylistSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StylistSlotRepository extends JpaRepository<StylistSlot, Long> {
    StylistSlot findStylistSlotByAccountAndSlotAndDate(Account staff, Slot slot, LocalDate date);
    List<StylistSlot> findStylistSlotByAccountAndDate(Account staff, LocalDate date);
    List<StylistSlot> findStylistSlotByDateAndSlotAndOrderDetailNull(LocalDate date, Slot slot);
    List<StylistSlot> findStylistSlotByDateAndSlotAndAccountServicesContainingAndOrderDetailNull(LocalDate date, Slot slot, Service service);

    @Query("SELECT s FROM StylistSlot s WHERE MONTH(s.date) = :month AND YEAR(s.date) = :year AND s.account.id = :id")
    List<StylistSlot> findStylistSlotByDate(@Param("month") int month, @Param("year") int year, @Param("id") long id);

    @Query("SELECT s FROM StylistSlot s WHERE MONTH(s.date) = :month AND YEAR(s.date) = :year AND s.account.store.id = :storeId")
    List<StylistSlot> findStylistSlotByDateManager(@Param("month") int month, @Param("year") int year, @Param("storeId") long storeId);
}
