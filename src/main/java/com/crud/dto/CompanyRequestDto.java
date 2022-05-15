package com.crud.dto;

import com.crud.entity.Address;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CompanyRequestDto {
    @NotBlank(message = "userName should not be blank")
    private String companyName;

    private String panCard;

    private String telephoneNumber;

    @NotBlank(message = "firstName should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;

    @Email
    private String email;

    @NotBlank(message = "userName should not be blank")
    private String username;

    @NotBlank(message = "password should not be blank")
    private String password;

    private Address address;

    @Getter
    @Setter
    public static class UpDateCompany {
        private String companyName;
    }
}
