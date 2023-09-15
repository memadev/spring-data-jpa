package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.dto.OrderDocument;
import com.eshaghi.spring.data.jpa.dto.OrderDto;
import com.eshaghi.spring.data.jpa.dto.OrderQuery;
import com.eshaghi.spring.data.jpa.service.CustomerService;
import com.eshaghi.spring.data.jpa.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static com.eshaghi.spring.data.jpa.config.AppProfile.INTEGRATION_TEST;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.createCustomer1;
import static com.eshaghi.spring.data.jpa.fixture.OrderFixture.ORDER_2_GRAND_TOTAL;
import static com.eshaghi.spring.data.jpa.fixture.OrderFixture.ORDER_2_REQUEST_UID;
import static com.eshaghi.spring.data.jpa.fixture.OrderFixture.createOrder1;
import static com.eshaghi.spring.data.jpa.fixture.OrderFixture.createOrder2;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@ActiveProfiles(INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class OrderControllerTest {

    static final String URL_FIND = "/api/orders/find";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper mapper;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeEach
    void cleanup() {
        customerService.deleteAll();
        orderService.deleteAll();
    }

    @Test
    public void test_shouldReturn400_WhenValidationFails() throws Exception {
        OrderQuery query = new OrderQuery(0, 0, 0);
        webTestClient.post()
                .uri(URL_FIND)
                .contentType(APPLICATION_JSON)
                .bodyValue(mapper.writeValueAsString(query))
                .exchange()
                .expectStatus().isEqualTo(BAD_REQUEST);
    }

    @Test
    public void test_shouldReturnFirstPage() throws Exception {
        long customerId = customerService.save(createCustomer1());
        orderService.save(createOrder1(customerId));
        orderService.save(createOrder2(customerId));

        OrderQuery query = new OrderQuery(customerId, 0, 1);
        OrderDocument document = webTestClient.post()
                .uri(URL_FIND)
                .contentType(APPLICATION_JSON)
                .bodyValue(mapper.writeValueAsString(query))
                .exchange()
                .expectStatus().isOk()
                .expectBody(OrderDocument.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(document);
        assertNotNull(document.orders());
        assertEquals(2, document.totalCount());

        OrderDto order2 = document.orders().get(0);

        assertEquals(ORDER_2_GRAND_TOTAL, new BigDecimal(order2.grandTotal()).setScale(2, HALF_UP));
        assertEquals(ORDER_2_REQUEST_UID, order2.requestUid());
    }
}
