package com.crud.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "userName should not be blank")
    private String username;

    @NotBlank(message = "password should not be blank")
    private String password;
}
