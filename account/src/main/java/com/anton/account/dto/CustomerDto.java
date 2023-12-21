package com.anton.account.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "email cannot be empty")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "phoneNUmber must be 10 digit")
    private String phoneNumber;
    private AccountDto accountsDto;

}
