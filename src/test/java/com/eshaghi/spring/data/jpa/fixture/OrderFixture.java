package com.eshaghi.spring.data.jpa.fixture;

import com.eshaghi.spring.data.jpa.domain.Order;
import com.eshaghi.spring.data.jpa.domain.OrderItem;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static com.eshaghi.spring.data.jpa.fixture.OrderItemFixture.createOrderItem1;
import static com.eshaghi.spring.data.jpa.fixture.OrderItemFixture.createOrderItem2;

public class OrderFixture {

    public static final BigDecimal ORDER_1_GRAND_TOTAL = new BigDecimal("111.11");
    public static final String ORDER_1_REQUEST_UID = UUID.randomUUID().toString();

    public static final BigDecimal ORDER_2_GRAND_TOTAL = new BigDecimal("222.22");
    public static final String ORDER_2_REQUEST_UID = UUID.randomUUID().toString();

    public static Order createOrder1(long customerId) {
        return createOrder(ORDER_1_REQUEST_UID,
                customerId,
                List.of(createOrderItem1()),
                ORDER_1_GRAND_TOTAL);
    }

    public static Order createOrder2(long customerId) {
        return createOrder(ORDER_2_REQUEST_UID,
                customerId,
                List.of(createOrderItem2()),
                ORDER_2_GRAND_TOTAL);
    }

    public static Order createOrder(String requestUid,
                                    long customerId,
                                    List<OrderItem> orderItems,
                                    BigDecimal grandTotal) {
        return Order.builder()
                .requestUid(requestUid)
                .customerId(customerId)
                .items(orderItems)
                .grandTotal(grandTotal)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
