package com.anton.account.service.impl;

import com.anton.account.dto.AccountDto;
import com.anton.account.dto.CustomerDto;
import com.anton.account.exception.CustomerAlreadyExistsException;
import com.anton.account.exception.ResourceNotFoundException;
import com.anton.account.mapper.AccountsMapper;
import com.anton.account.mapper.CustomerMapper;
import com.anton.account.model.AccountType;
import com.anton.account.model.Accounts;
import com.anton.account.model.Customer;
import com.anton.account.repository.AccountRepository;
import com.anton.account.repository.CustomerRepository;
import com.anton.account.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    public static final Long accountSeedId = 100000000000000L;

    @Override
    public void createAccount(CustomerDto dto) {

        Customer c = CustomerMapper.mapToCustomer(dto, new Customer());
//        c.setCreatedAt(LocalDateTime.now());
//        c.setCreatedBy("someone");
        Optional<Customer> optionalCustomer = customerRepository.findByPhoneNumber(dto.getPhoneNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Already exists");
        }
        Customer savedCustomer = customerRepository.save(c);
        createAccounts(savedCustomer);
    }

    public Optional<Customer> getEmptyCustomer() {
        return Optional.empty();
    }


    private Accounts createAccounts(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        Long accId = accountSeedId + ThreadLocalRandom.current().nextLong(800000000000000L);
        accounts.setAccountNumber(accId);
        accounts.setAccountType(AccountType.SAVINGS.toString());
        accounts.setBranchAddress("TEMP");
//        accounts.setCreatedAt(customer.getCreatedAt());
//        accounts.setCreatedBy(customer.getCreatedBy());
        return accountRepository.save(accounts);
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByPhoneNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountDto()));
        return customerDto;
    }

    /**
     * @param customerDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null ) {
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByPhoneNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }


}
