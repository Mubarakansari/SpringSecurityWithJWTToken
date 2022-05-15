package com.crud.service;

import com.crud.config.GenericResponse;
import com.crud.dto.CompanyRequestDto;
import com.crud.dto.LoginRequest;
import com.crud.dto.UserAndAddressDto;
import com.crud.entity.Address;

import java.util.Set;

public interface CompanyService {

    GenericResponse signUp(CompanyRequestDto company);

    GenericResponse login(LoginRequest loginRequest);

    GenericResponse update(Long companyId, CompanyRequestDto.UpDateCompany company);

    GenericResponse fetchById(String token, Long companyId);

    GenericResponse fetchAll();

    GenericResponse saveUserAndAddress(Long userId, Set<Address> addresses);

    GenericResponse delete(Long companyId);
}
