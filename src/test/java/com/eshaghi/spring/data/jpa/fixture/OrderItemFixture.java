package com.eshaghi.spring.data.jpa.fixture;

import com.eshaghi.spring.data.jpa.domain.OrderItem;
import java.math.BigDecimal;

public class OrderItemFixture {

    public static final String ORDER_ITEM_NAME_1 = "OrderItem1";
    public static final BigDecimal ORDER_ITEM_PRICE_1 = new BigDecimal("11.11");
    public static final int ORDER_ITEM_QUANTITY_1 = 2;
    public static final BigDecimal ORDER_ITEM_TOTAL_1 = new BigDecimal("22.22");

    public static final String ORDER_ITEM_NAME_2 = "OrderItem2";
    public static final BigDecimal ORDER_ITEM_PRICE_2 = new BigDecimal("22.22");
    public static final int ORDER_ITEM_QUANTITY_2 = 3;
    public static final BigDecimal ORDER_ITEM_TOTAL_2 = new BigDecimal("66.66");

    public static OrderItem createOrderItem1() {
        return createOrderItem(ORDER_ITEM_NAME_1,
                ORDER_ITEM_PRICE_1,
                ORDER_ITEM_QUANTITY_1,
                ORDER_ITEM_TOTAL_1);
    }

    public static OrderItem createOrderItem2() {
        return createOrderItem(ORDER_ITEM_NAME_2,
                ORDER_ITEM_PRICE_2,
                ORDER_ITEM_QUANTITY_2,
                ORDER_ITEM_TOTAL_2);
    }

    public static OrderItem createOrderItem(String name,
                                            BigDecimal price,
                                            int quantity,
                                            BigDecimal total) {
        return OrderItem.builder()
                .name(name)
                .price(price)
                .quantity(quantity)
                .total(total)
                .build();
    }
}
