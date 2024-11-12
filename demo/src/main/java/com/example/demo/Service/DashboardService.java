package com.example.demo.Service;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Enums.OrderStatus;
import com.example.demo.Entity.Enums.Role;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Store;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DashboardService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    OrderRepository orderRepository;


    public HashMap<String, Float> getTotal(){
        float totalOrders = 0;
        float totalServices = 0;
        float totalStore = 0;
        float totalStaff = 0;
        float totalRevenue = 0;
        HashMap<String, Float> response = new HashMap<>();

        for(com.example.demo.Entity.Service service : serviceRepository.findAll()){
            totalServices +=1;
        }
        for (Order order : orderRepository.findOrdersByStatus(OrderStatus.PAID)){
            totalOrders +=1;
        }
        for (Store store : storeRepository.findAll()){
            totalStore +=1;
        }
        for (Account staff : accountRepository.findAccountsByRole(Role.STAFF)){
            totalStaff++;
        }
        for(Order order : orderRepository.findAll()){
             totalRevenue +=order.getTotal();
        }

        response.put("totalServices", totalServices);
        response.put("totalOrders", totalOrders);
        response.put("totalStore", totalStore);
        response.put("totalStaff", totalStaff);
        response.put("totalRevenue", totalRevenue);

        return response;

    }

}
