package com.eshaghi.spring.data.jpa.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(long id,
                       String requestUid,
                       long customerId,
                       List<OrderItemDto> items,
                       String grandTotal,
                       LocalDateTime createdAt) {
}
