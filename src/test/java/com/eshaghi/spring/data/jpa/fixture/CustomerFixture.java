package com.eshaghi.spring.data.jpa.fixture;

import com.eshaghi.spring.data.jpa.domain.Customer;
import java.util.List;
import static com.eshaghi.spring.data.jpa.fixture.AccountFixture.ACCOUNT_NUMBER_1;
import static com.eshaghi.spring.data.jpa.fixture.AccountFixture.ACCOUNT_NUMBER_2;
import static com.eshaghi.spring.data.jpa.fixture.AccountFixture.createAccount;

public class CustomerFixture {

    public static final String CUSTOMER_1_PERSON_NUMBER = "198011111111";
    public static final String CUSTOMER_1_FIRST_NAME = "John1";
    public static final String CUSTOMER_1_LAST_NAME = "Smith1";

    public static final String CUSTOMER_2_PERSON_NUMBER = "199011222222";
    public static final String CUSTOMER_2_FIRST_NAME = "John2";
    public static final String CUSTOMER_2_LAST_NAME = "Smith3";

    public static Customer createCustomer1() {
        return createCustomer(CUSTOMER_1_PERSON_NUMBER,
                CUSTOMER_1_FIRST_NAME,
                CUSTOMER_1_LAST_NAME,
                ACCOUNT_NUMBER_1);
    }

    public static Customer createCustomer2() {
        return createCustomer(CUSTOMER_2_PERSON_NUMBER,
                CUSTOMER_2_FIRST_NAME,
                CUSTOMER_2_LAST_NAME,
                ACCOUNT_NUMBER_2);
    }

    public static Customer createCustomer(String personNumber,
                                          String firstName,
                                          String lastName,
                                          String accountNumber) {
        return Customer.builder()
                .personNumber(personNumber)
                .firstName(firstName)
                .lastName(lastName)
                .accounts(List.of(createAccount(accountNumber)))
                .build();
    }
}
