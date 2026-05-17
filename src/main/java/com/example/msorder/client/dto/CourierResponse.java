package com.example.msorder.client.dto;


import com.example.msorder.client.enums.CourierStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourierResponse {
    private Long id;
    private String name;
    private CourierStatus status;
}
