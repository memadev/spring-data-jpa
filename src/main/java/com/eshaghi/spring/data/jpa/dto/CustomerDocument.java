package com.eshaghi.spring.data.jpa.dto;

import java.util.List;

public record CustomerDocument(List<CustomerDto> customers) {
}
