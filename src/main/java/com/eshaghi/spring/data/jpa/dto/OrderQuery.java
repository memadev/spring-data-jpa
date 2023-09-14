package com.eshaghi.spring.data.jpa.dto;

public record OrderQuery(long customerId,
                         int pageNo,
                         int pageSize) {
}
