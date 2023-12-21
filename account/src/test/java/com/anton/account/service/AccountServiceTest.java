package com.anton.account.service;

import com.anton.account.dto.AccountDto;
import com.anton.account.dto.CustomerDto;
import com.anton.account.model.Accounts;
import com.anton.account.model.Customer;
import com.anton.account.repository.AccountRepository;
import com.anton.account.repository.CustomerRepository;
import com.anton.account.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class AccountServiceTest {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void emptyCustomerIfNoCustomers() {
        var customerService = new AccountServiceImpl(accountRepository, customerRepository);
        var expectedValue = Optional.empty();
        var actualValue = customerService.getEmptyCustomer();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void givenPhoneNumber_whenGetByPhoneNumber_thenReturnCustomer() {
        var customerService = new AccountServiceImpl(accountRepository, customerRepository);
        Customer customer = new Customer(1L, "AntonTest", "test@gmail.com", "19191919191");
        Accounts account = new Accounts(1L, 1L, "SAVINGS", "some address");
        var expectedCustomerDto =
                new CustomerDto("AntonTest", "test@gmail.com", "19191919191", new AccountDto(null, 1L, "SAVINGS", "some address"));

        given(customerRepository.findByPhoneNumber("19191919191")).willReturn(Optional.of(customer));
        given(accountRepository.findByCustomerId(1L)).willReturn(Optional.of(account));

        assertEquals(customerService.fetchAccount("19191919191"), expectedCustomerDto);
    }


}
