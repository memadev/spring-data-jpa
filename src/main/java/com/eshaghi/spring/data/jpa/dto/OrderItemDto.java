package com.eshaghi.spring.data.jpa.dto;

public record OrderItemDto(long id,
                           String name,
                           String price,
                           int quantity,
                           String total) {
}
