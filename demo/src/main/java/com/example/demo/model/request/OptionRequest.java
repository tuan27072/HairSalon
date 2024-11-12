package com.example.demo.model.request;

import lombok.Data;

@Data
public class OptionRequest {
    String name;
    String description;
    long serviceId;
    String image;
    Float price;
}
