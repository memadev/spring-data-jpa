package com.eshaghi.spring.data.jpa.dto;

import java.util.List;

public record CustomerDto(long id,
                          String personNumber,
                          String firstName,
                          String lastName,
                          List<AccountDto> accounts) {
}
