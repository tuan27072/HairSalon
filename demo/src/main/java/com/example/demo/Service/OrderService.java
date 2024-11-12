package com.example.demo.Service;

import com.example.demo.Entity.*;
import com.example.demo.Entity.Enums.OrderStatus;
import com.example.demo.exception.BadRequestException;
import com.example.demo.model.request.FeedbackRequest;
import com.example.demo.model.request.OrderDetailRequest;
import com.example.demo.model.request.OrderRequest;
import com.example.demo.repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    AccountService accountService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SlotRepository slotRepository;
    @Autowired
    StylistSlotRepository stylistSlotRepository;
    @Autowired
    OrderDetailRepository orderDetailRepository;

    public Order createNewBooking(OrderRequest orderRequest) {
        Order order = new Order();
        List<OrderDetail> orderDetails = new ArrayList<>();
        float total = 0;
        Store store = storeRepository.findStoreById(orderRequest.getStoreId());
        List<ServiceOption> serviceOptionList = new ArrayList<>();

        // first slot
        Slot firstSlot = slotRepository.findSlotById(orderRequest.getSlotId());

        for (int i = 0; i < orderRequest.getOrderDetailRequests().size(); i++) {
            Slot slot;

            if(i == 0){
                slot = firstSlot;
            }else{
                slot = slotRepository.findSlotById(firstSlot.getId() + i);
            }

            OrderDetailRequest orderDetailRequest = orderRequest.getOrderDetailRequests().get(i);
            ServiceOption serviceOption = optionRepository.findOptionById(orderDetailRequest.getOptionId());
            Account staff = accountRepository.findAccountById(orderDetailRequest.getStaffId());
            StylistSlot stylistSlot = stylistSlotRepository.findStylistSlotByAccountAndSlotAndDate(staff, slot, orderRequest.getDate());

            if (stylistSlot.getOrderDetail() != null) {
                //staff unavailable
                throw new BadRequestException("This staff not available!!!");
            }

            List<StylistSlot> stylistSlots = new ArrayList<>();
            stylistSlots.add(stylistSlot);
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(1);
            orderDetail.setPrice(serviceOption.getPrice());
            orderDetail.setOrder(order);
            orderDetail.setServiceOption(serviceOption);
            orderDetail.setStylistSlots(stylistSlots);
            orderDetails.add(orderDetail);
            total += orderDetail.getPrice();
            stylistSlot.setOrderDetail(orderDetail);
        }
        order.setOrderDetails(orderDetails);
        order.setDate(orderRequest.getDate());
        order.setTotal(total);
        order.setStore(store);
        order.setAccount(accountService.getCurrentAccount());
        return orderRepository.save(order);
    }

    public Order updateBooking(Long orderId, OrderRequest orderRequest) {
        // Fetch the existing order by ID
        Order order = orderRepository.findOrderById(orderId);

        // Initialize a new list of order details and total price
        List<OrderDetail> updatedOrderDetails = new ArrayList<>();
        float total = 0;

        // Fetch the updated store and slot based on the new request
        Store store = storeRepository.findStoreById(orderRequest.getStoreId());
        Slot slot = slotRepository.findSlotById(orderRequest.getSlotId());

        // Update order details by iterating over the new request details
        for (OrderDetailRequest orderDetailRequest : orderRequest.getOrderDetailRequests()) {
            ServiceOption serviceOption = optionRepository.findOptionById(orderDetailRequest.getOptionId());
            Account staff = accountRepository.findAccountById(orderDetailRequest.getStaffId());
            StylistSlot stylistSlot = stylistSlotRepository.findStylistSlotByAccountAndSlotAndDate(staff, slot, orderRequest.getDate());

            // Create a new OrderDetail and set its properties
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(1);
            orderDetail.setPrice(serviceOption.getPrice());
            orderDetail.setOrder(order);
            orderDetail.setServiceOption(serviceOption);

            // Save the order detail to persist it in the database
            orderDetail = orderDetailRepository.save(orderDetail);

            // Set the stylist slot for the detail
            List<StylistSlot> stylistSlots = new ArrayList<>();
            stylistSlots.add(stylistSlot);
            orderDetail.setStylistSlots(stylistSlots);
            stylistSlot.setOrderDetail(orderDetail);

            // Add the updated detail to the list and calculate the total price
            updatedOrderDetails.add(orderDetail);
            total += orderDetail.getPrice();
        }

        // Update the order object with the new details
        order.setOrderDetails(updatedOrderDetails);
        order.setDate(orderRequest.getDate());
        order.setTotal(total);
        order.setStore(store);

        // Update the account if necessary (current logged-in user)
        order.setAccount(accountService.getCurrentAccount());

        // Save and return the updated order
        return orderRepository.save(order);
    }

    public List<Order> getAllOrder() {
        return orderRepository.findOrdersByAccount(accountService.getCurrentAccount());
    }


    public List<Order> getAllOrderAdmin() {
        return orderRepository.findAll();
    }

    public Order updateStatus(long orderId, OrderStatus status) {
        Order order = orderRepository.findOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Order createFeedback(long orderId, FeedbackRequest feedbackRequest) {
        Order order = orderRepository.findOrderById(orderId);
        order.setRate(feedbackRequest.getRate());
        order.setFeedback(feedbackRequest.getFeedback());
        return orderRepository.save(order);
    }
}
