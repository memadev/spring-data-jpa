package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.domain.Customer;
import com.eshaghi.spring.data.jpa.repository.CustomerRepository;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterEach;
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
import reactor.core.publisher.Mono;
import static com.eshaghi.spring.data.jpa.config.AppProfile.INTEGRATION_TEST;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.createCustomer1;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.createCustomer2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.CONFLICT;

@Testcontainers
@ActiveProfiles(INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CustomerControllerTest {

    static final String URL = "/api/customers";
    static final String URL_WITH_PERSON_NUMBER = "/api/customers/%s";

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    CustomerRepository customerRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeEach
    void init() {
        customerRepository.saveAll(List.of(createCustomer1(), createCustomer2()));
    }

    @AfterEach
    void cleanup() {
        customerRepository.deleteAll();
    }

    @Test
    public void test_shouldReturnAll() {
        List<Customer> customers = webTestClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Customer.class)
                .returnResult()
                .getResponseBody();

        assertEquals(2, customers.size());
    }

    @Test
    public void test_shouldReturn404_WhenCustomerNotFound_OnGet() {
        int randomPersonNumber = new Random().nextInt();
        webTestClient.get()
                .uri(String.format(URL_WITH_PERSON_NUMBER, randomPersonNumber))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_shouldReturn409_OnDuplicateAdd() {
        webTestClient.post()
                .uri("/api/customers")
                .body(Mono.just(createCustomer1()), Customer.class)
                .exchange()
                .expectStatus().isEqualTo(CONFLICT);
    }
}
