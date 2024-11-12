package com.example.demo.model.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class OrderRequest {
    long storeId;
    List<OrderDetailRequest> orderDetailRequests;
    long slotId;
    LocalDate date;
}
