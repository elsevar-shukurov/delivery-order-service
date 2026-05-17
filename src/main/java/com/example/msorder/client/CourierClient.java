package com.example.msorder.client;

import com.example.msorder.client.dto.CourierResponse;
import com.example.msorder.client.enums.CourierStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "ms-courier", url = "http://localhost:8082")
public interface CourierClient {

    @GetMapping("/couriers/available")
    List<CourierResponse> getAvailableCouriers();

    @GetMapping("/couriers/{id}")
    CourierResponse getCourierById(@PathVariable Long id);

    @PutMapping("/couriers/{id}/status")
    void updateCourierStatus(@PathVariable Long id, @RequestParam CourierStatus status);
}
