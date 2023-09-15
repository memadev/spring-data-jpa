package com.eshaghi.spring.data.jpa.dto;

import javax.validation.constraints.Min;

public record OrderQuery(@Min(1) long customerId,
                         @Min(0) int pageNo,
                         @Min(1) int pageSize) {
}
