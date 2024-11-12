package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Enums.OrderStatus;
import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByAccount(Account account);
    Order findOrderById(Long orderId);

    List<Order> findOrdersByStatus(OrderStatus orderStatus);
}
