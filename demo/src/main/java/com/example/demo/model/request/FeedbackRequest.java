package com.example.demo.model.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    int rate;
    String feedback;
}
