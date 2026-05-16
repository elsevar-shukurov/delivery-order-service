package com.example.msorder.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long id;
    private String status;
    private String pickupAddress;
    private String deliveryAddress;
    private BigDecimal price;
    private Long courierId;
    private String courierName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
