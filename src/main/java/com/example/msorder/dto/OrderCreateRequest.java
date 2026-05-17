package com.example.msorder.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {

    private String pickupAddress;

    private String deliveryAddress;
}
