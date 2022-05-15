package com.crud.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserAndAddressDto {
    private Long userId;
    private List<Address> addresses = new ArrayList<>();

    @Setter
    @Getter
    private static class Address {
        private String pinCode;
        private String state;
        private String city;
    }
}
