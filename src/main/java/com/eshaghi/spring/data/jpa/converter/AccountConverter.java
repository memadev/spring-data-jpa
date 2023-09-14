package com.eshaghi.spring.data.jpa.converter;

import com.eshaghi.spring.data.jpa.domain.Account;
import com.eshaghi.spring.data.jpa.dto.AccountDto;
import java.util.List;

public class AccountConverter {

    public static List<AccountDto> convertToDtoList(List<Account> accounts) {
        return accounts.stream()
                .map(AccountConverter::convertToDto)
                .toList();
    }

    private static AccountDto convertToDto(Account account) {
        return new AccountDto(account.getId(),
                account.getAccountNumber());
    }

    public static List<Account> convertToList(List<AccountDto> accounts) {
        return accounts.stream()
                .map(AccountConverter::convert)
                .toList();
    }

    private static Account convert(AccountDto dto) {
        return new Account(dto.id(),
                dto.accountNumber());
    }
}
