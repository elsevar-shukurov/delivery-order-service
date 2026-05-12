package com.example.msorder.mapper;


import com.example.msorder.dao.Order;
import com.example.msorder.dto.OrderCreateRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.enums.OrderStatus;

public class OrderMapper {

    public static Order toEntity(OrderCreateRequest request) {
        return Order.builder()
                .pickupAddress(request.getPickupAddress())
                .deliveryAddress(request.getDeliveryAddress())
                .status(OrderStatus.CREATED)
                .build();
    }

    public static OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus().toString())
                .pickupAddress(order.getPickupAddress())
                .deliveryAddress(order.getDeliveryAddress())
                .price(order.getPrice())
                .courierId(order.getCourierId())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
