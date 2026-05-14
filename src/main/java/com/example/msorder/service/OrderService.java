package com.example.msorder.service;

import com.example.msorder.dao.Order;
import com.example.msorder.dao.OrderRepository;
import com.example.msorder.dto.OrderCreateRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.enums.OrderStatus;
import com.example.msorder.exceptions.InvalidOrderStatusException;
import com.example.msorder.exceptions.OrderNotFoundException;
import com.example.msorder.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

import static com.example.msorder.enums.OrderStatus.DELIVERED;
import static com.example.msorder.mapper.OrderMapper.toResponse;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void createOrder(OrderCreateRequest orderCreateRequest) {
        BigDecimal calculatedPrice = calculatePrice(orderCreateRequest);
        Order order= OrderMapper.toEntity(orderCreateRequest);
        order.setCourierId(1L);
        order.setStatus(OrderStatus.ASSIGNED);
        order.setPrice(calculatedPrice);
        orderRepository.save(order);
    }

    public List<OrderResponse> getAllOrders() {
        List<OrderResponse> responseList= orderRepository.findAll().stream().
                map(m-> toResponse(m)).toList();

        return responseList;
    }

    public OrderResponse getOrderById(Long id) {
        return toResponse(fetchOrderIfExists(id));
    }

    public void updateStatusToDelivered(Long id){
        Order order = fetchOrderIfExists(id);
        if (order.getStatus() != OrderStatus.ASSIGNED) {
            throw new InvalidOrderStatusException("Order can be delivered only when status is ASSIGNED, current: "
                    + order.getStatus());
        }
        order.setStatus(DELIVERED);
        orderRepository.save(order);
    }

    private Order fetchOrderIfExists(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private BigDecimal calculatePrice(OrderCreateRequest request) {
        return BigDecimal.valueOf(10.00);
    }
}
