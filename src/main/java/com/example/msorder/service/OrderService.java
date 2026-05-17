package com.example.msorder.service;

import com.example.msorder.client.CourierClient;
import com.example.msorder.client.dto.CourierResponse;
import com.example.msorder.dao.Order;
import com.example.msorder.dao.OrderRepository;
import com.example.msorder.dto.OrderCreateRequest;
import com.example.msorder.dto.OrderResponse;
import com.example.msorder.exceptions.CourierUnavailableException;
import com.example.msorder.exceptions.InvalidOrderStatusException;
import com.example.msorder.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static com.example.msorder.client.enums.CourierStatus.BUSY;
import static com.example.msorder.enums.OrderStatus.ASSIGNED;
import static com.example.msorder.enums.OrderStatus.DELIVERED;
import static com.example.msorder.mapper.OrderMapper.toEntity;
import static com.example.msorder.mapper.OrderMapper.toResponse;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CourierClient courierClient;

    public void createOrder(OrderCreateRequest request) {
        var calculatedPrice = calculatePrice(request);

        List<CourierResponse> availableCouriers = courierClient.getAvailableCouriers();
        if (availableCouriers.isEmpty()) {
            throw new CourierUnavailableException();
        }

        CourierResponse assignedCourier = availableCouriers.getFirst();

        System.out.println(assignedCourier.getId());
        System.out.println(assignedCourier.getName());
        courierClient.updateCourierStatus(assignedCourier.getId(), BUSY);

        var order= toEntity(request, assignedCourier.getId(), calculatedPrice, ASSIGNED);
        orderRepository.save(order);
    }

    public List<OrderResponse> getAllOrders() {
        List<OrderResponse> responseList= orderRepository.findAll().stream().
                map(m-> toResponse(m)).toList();

        return responseList;
    }

    public OrderResponse getOrderById(Long id) {
        Order order= fetchOrderIfExists(id);
        OrderResponse orderResponse= toResponse(order);

        CourierResponse courier= courierClient.getCourierById(order.getCourierId());
        orderResponse.setCourierName(courier.getName());

        return orderResponse;
    }

    public void updateStatusToDelivered(Long id){
        Order order = fetchOrderIfExists(id);
        if (order.getStatus() != ASSIGNED) {
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
