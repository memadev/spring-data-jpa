package com.eshaghi.spring.data.jpa.converter;

import com.eshaghi.spring.data.jpa.domain.Order;
import com.eshaghi.spring.data.jpa.dto.OrderDto;
import java.util.List;

public class OrderConverter {

    public static List<OrderDto> convertToDtoList(List<Order> orders) {
        return orders.stream()
                .map(OrderConverter::convert)
                .toList();
    }

    public static OrderDto convert(Order order) {
        return new OrderDto(order.getId(),
                order.getRequestUid(),
                order.getCustomerId(),
                OrderItemConverter.convertToDtoList(order.getItems()),
                order.getGrandTotal().toString(),
                order.getCreatedAt());
    }
}
