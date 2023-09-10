package com.eshaghi.spring.data.jpa.dto;

public record CustomerQuery(String personNumber,
                            String firstName,
                            String lastName) {
}
