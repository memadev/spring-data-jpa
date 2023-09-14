package com.eshaghi.spring.data.jpa.converter;

import com.eshaghi.spring.data.jpa.domain.OrderItem;
import com.eshaghi.spring.data.jpa.dto.OrderItemDto;
import java.math.BigDecimal;
import java.util.List;

public class OrderItemConverter {

    public static List<OrderItemDto> convertToDtoList(List<OrderItem> items) {
        return items.stream()
                .map(OrderItemConverter::convertToDto)
                .toList();
    }

    private static OrderItemDto convertToDto(OrderItem item) {
        return new OrderItemDto(item.getId(),
                item.getName(),
                item.getPrice().toString(),
                item.getQuantity(),
                item.getTotal().toString());
    }

    public static List<OrderItem> convertToList(List<OrderItemDto> items) {
        return items.stream()
                .map(OrderItemConverter::convert)
                .toList();
    }

    private static OrderItem convert(OrderItemDto dto) {
        return new OrderItem(dto.id(),
                dto.name(),
                new BigDecimal(dto.price()),
                dto.quantity(),
                new BigDecimal(dto.total()),
                null);//TODO: fix this
    }
}
