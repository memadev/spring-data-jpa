package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.domain.Order;
import com.eshaghi.spring.data.jpa.dto.OrderDocument;
import com.eshaghi.spring.data.jpa.dto.OrderQuery;
import com.eshaghi.spring.data.jpa.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.eshaghi.spring.data.jpa.config.AppProfile.INTEGRATION_TEST;
import static com.eshaghi.spring.data.jpa.config.AppProfile.NORMAL;
import static com.eshaghi.spring.data.jpa.converter.OrderConverter.convertToDtoList;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@Profile({NORMAL, INTEGRATION_TEST})
@AllArgsConstructor
class OrderController {

    private OrderService service;

    @Validated
    @PostMapping("/find")
    public OrderDocument find(@RequestBody OrderQuery query) {
        List<Order> orders = service.findByCustomerId(query.customerId(), query.pageNo(), query.pageSize());
        var totalCount = service.countByCustomerId(query.customerId());
        return new OrderDocument(convertToDtoList(orders), totalCount);
    }
}
