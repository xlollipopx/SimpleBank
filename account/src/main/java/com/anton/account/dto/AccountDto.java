package com.anton.account.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long customerId;

    @NotEmpty(message = "accountNumber cannot be empty")
    private Long accountNumber;

    @NotEmpty(message = "accountType cannot be empty")
    private String accountType;

    @NotEmpty(message = "branchAddress cannot be empty")
    private String branchAddress;
}
