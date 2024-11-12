package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
