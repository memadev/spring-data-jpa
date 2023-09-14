package com.eshaghi.spring.data.jpa.dto;

import java.util.List;

public record OrderDocument(List<OrderDto> orders,
                            int totalCount) {
}
