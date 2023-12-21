package com.anton.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDto {
    String apiPath;
    HttpStatus errorCode;
    String errorMessage;

}
