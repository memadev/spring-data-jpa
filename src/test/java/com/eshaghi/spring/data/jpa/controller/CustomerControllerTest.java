package com.eshaghi.spring.data.jpa.controller;

import com.eshaghi.spring.data.jpa.domain.Customer;
import com.eshaghi.spring.data.jpa.dto.CustomerDto;
import com.eshaghi.spring.data.jpa.dto.CustomerQuery;
import com.eshaghi.spring.data.jpa.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Random;
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
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_1_FIRST_NAME;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_1_LAST_NAME;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_1_PERSON_NUMBER;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_2_FIRST_NAME;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_2_LAST_NAME;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.CUSTOMER_2_PERSON_NUMBER;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.createCustomer1;
import static com.eshaghi.spring.data.jpa.fixture.CustomerFixture.createCustomer2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Testcontainers
@ActiveProfiles(INTEGRATION_TEST)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class CustomerControllerTest {

    static final String URL = "/api/customers";
    static final String URL_FIND = "/api/customers/find";
    static final String URL_WITH_PERSON_NUMBER = "/api/customers/%s";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper mapper;

    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @BeforeEach
    void cleanup() {
        customerService.deleteAll();
    }

    @Test
    public void test_shouldReturnAll() {
        customerService.save(createCustomer1());
        customerService.save(createCustomer2());

        List<CustomerDto> customers = webTestClient.get()
                .uri(URL)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(2, customers.size());
    }

    @Test
    public void test_shouldReturnCustomer1() throws Exception {
        customerService.save(createCustomer1());
        customerService.save(createCustomer2());

        CustomerQuery query = new CustomerQuery(CUSTOMER_1_PERSON_NUMBER, "", "");
        List<CustomerDto> customers = webTestClient.post()
                .uri(URL_FIND)
                .contentType(APPLICATION_JSON)
                .bodyValue(mapper.writeValueAsString(query))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(1, customers.size());
        CustomerDto returnedCustomer1 = customers.get(0);
        assertEquals(CUSTOMER_1_PERSON_NUMBER, returnedCustomer1.personNumber());
        assertEquals(CUSTOMER_1_FIRST_NAME, returnedCustomer1.firstName());
        assertEquals(CUSTOMER_1_LAST_NAME, returnedCustomer1.lastName());
    }

    @Test
    public void test_shouldReturn404_WhenCustomerNotFound() {
        int randomPersonNumber = new Random().nextInt();
        webTestClient.get()
                .uri(String.format(URL_WITH_PERSON_NUMBER, randomPersonNumber))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_shouldReturnCustomer2() {
        customerService.save(createCustomer2());

        CustomerDto returnedCustomer2 = webTestClient.get()
                .uri(String.format(URL_WITH_PERSON_NUMBER, CUSTOMER_2_PERSON_NUMBER))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(CUSTOMER_2_PERSON_NUMBER, returnedCustomer2.personNumber());
        assertEquals(CUSTOMER_2_FIRST_NAME, returnedCustomer2.firstName());
        assertEquals(CUSTOMER_2_LAST_NAME, returnedCustomer2.lastName());
    }

    @Test
    public void test_shouldReturn200_OnAdd() {
        webTestClient.post()
                .uri(URL)
                .body(Mono.just(createCustomer1()), Customer.class)
                .exchange()
                .expectStatus().isOk();

        //Check to see if customer1 exist
        CustomerDto addedCustomer1 = webTestClient.get()
                .uri(String.format(URL_WITH_PERSON_NUMBER, CUSTOMER_1_PERSON_NUMBER))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CustomerDto.class)
                .returnResult()
                .getResponseBody();

        assertEquals(CUSTOMER_1_PERSON_NUMBER, addedCustomer1.personNumber());
        assertEquals(CUSTOMER_1_FIRST_NAME, addedCustomer1.firstName());
        assertEquals(CUSTOMER_1_LAST_NAME, addedCustomer1.lastName());
    }

    @Test
    public void test_shouldReturn200_OnDuplicateAdd() {
        customerService.save(createCustomer1());

        webTestClient.post()
                .uri(URL)
                .body(Mono.just(createCustomer1()), Customer.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void test_shouldReturn200_OnDelete() {
        customerService.save(createCustomer1());

        webTestClient.delete()
                .uri(String.format(URL_WITH_PERSON_NUMBER, CUSTOMER_1_PERSON_NUMBER))
                .exchange()
                .expectStatus().isOk();

        //Check to see if customer1 is deleted
        webTestClient.get()
                .uri(String.format(URL_WITH_PERSON_NUMBER, CUSTOMER_1_PERSON_NUMBER))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void test_shouldReturn200_OnDelete_WhenCustomerDoesNotExist() {
        webTestClient.delete()
                .uri(String.format(URL_WITH_PERSON_NUMBER, CUSTOMER_1_PERSON_NUMBER))
                .exchange()
                .expectStatus().isOk();
    }
}
