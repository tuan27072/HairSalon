package com.example.demo.api;

import com.example.demo.Entity.Enums.OrderStatus;
import com.example.demo.Entity.Order;
import com.example.demo.Service.OrderService;
import com.example.demo.model.request.FeedbackRequest;
import com.example.demo.model.request.OrderRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@SecurityRequirement(name="api")
public class BookingAPI {
    @Autowired
    OrderService orderService;
    @GetMapping("booking")
    public ResponseEntity getAllBooking(){
        List<Order> orders = orderService.getAllOrder();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("booking/all")
    public ResponseEntity getAllBookingAdmin(){
        List<Order> orders = orderService.getAllOrderAdmin();
        return ResponseEntity.ok(orders);
    }
    //POST
    @PostMapping("booking")
    public ResponseEntity createNewBooking(@RequestBody OrderRequest orderRequest){
        Order order =orderService.createNewBooking(orderRequest);
        return ResponseEntity.ok(order);
    }

    @PatchMapping("booking/{orderId}")
    public ResponseEntity updateStatus(@PathVariable long orderId, @RequestParam OrderStatus status){
        Order order =orderService.updateStatus(orderId, status);
        return ResponseEntity.ok(order);
    }
    @PutMapping ("booking/{orderId}")
    public ResponseEntity updateBooking(@RequestBody OrderRequest orderRequest, @PathVariable long orderId){
        Order order =orderService.updateBooking(orderId, orderRequest);
        return ResponseEntity.ok(order);
    }
    @PostMapping("/booking/feedback/{orderId}")
    public ResponseEntity createFeedback(@PathVariable long orderId, @RequestBody FeedbackRequest feedbackRequest){
        Order order =orderService.createFeedback(orderId, feedbackRequest);
        return ResponseEntity.ok(order);
    }
}
