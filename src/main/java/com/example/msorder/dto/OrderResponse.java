package com.example.msorder.dto;

import com.example.msorder.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class OrderResponse {
    private Long id;
    private OrderStatus status;
    private String pickupAddress;
    private String deliveryAddress;
    private BigDecimal price;
    private Long courierId;
    private String courierName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
