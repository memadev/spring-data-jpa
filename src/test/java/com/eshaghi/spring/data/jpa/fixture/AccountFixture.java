package com.eshaghi.spring.data.jpa.fixture;

import com.eshaghi.spring.data.jpa.domain.Account;
import static com.eshaghi.spring.data.jpa.fixture.CurrencyFixture.EUR;

public class AccountFixture {

    public static final String ACCOUNT_NUMBER_1 = "2235-1111";
    public static final String ACCOUNT_NUMBER_2 = "2235-2222";

    public static Account createAccount(String accountNumber) {
        return Account.builder()
                .accountNumber(accountNumber)
                .currencyUid(EUR)
                .build();
    }
}
