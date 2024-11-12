package com.example.demo.repository;


import com.example.demo.Entity.Service;
import com.example.demo.Entity.ServiceOption;
import com.example.demo.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<ServiceOption, Long> {
    ServiceOption findOptionById(long id);
    List<ServiceOption> findOptionsByIsDeletedFalse();
}
