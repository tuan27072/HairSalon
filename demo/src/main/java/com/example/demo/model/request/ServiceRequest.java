package com.example.demo.model.request;

import lombok.Data;

@Data
public class ServiceRequest {
    String name;
    String description;
    String image;
    long categoryId;
}
