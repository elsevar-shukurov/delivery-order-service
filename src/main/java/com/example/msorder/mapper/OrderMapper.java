package com.example.msorder.mapper;


import com.example.msorder.dao.Order;
import com.example.msorder.dto.OrderCreateRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.enums.OrderStatus;

import java.math.BigDecimal;

public class OrderMapper {

    public static Order toEntity(OrderCreateRequest request, Long courierId, BigDecimal price, OrderStatus status) {
        return Order.builder()
                .pickupAddress(request.getPickupAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .courierId(courierId)
                .price(price)
                .status(status)
                .build();
    }

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .pickupAddress(order.getPickupAddress())
                .deliveryAddress(order.getDeliveryAddress())
                .price(order.getPrice())
                .courierId(order.getCourierId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
