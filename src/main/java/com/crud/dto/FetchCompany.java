package com.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FetchCompany {

    private Long companyId;
    private String companyName;
    private String panCard;
    private String telephoneNumber;
    private String pinCode;

}
