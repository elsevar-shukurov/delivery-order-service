package com.example.msorder.controller;

import com.example.msorder.dto.OrderCreateRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @ResponseStatus(OK)
    public List<OrderResponse> getAllOrders() {
       return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public OrderResponse getOrdersById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createOrder(@RequestBody OrderCreateRequest orderCreateRequest) {
        orderService.createOrder(orderCreateRequest);
    }

    @PatchMapping("/{id}/deliver")
    @ResponseStatus(OK)
    public void updateOrderStatus(@PathVariable Long id) {
        orderService.updateStatusToDelivered(id);
    }

}
