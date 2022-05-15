package com.crud.service.serviceImpl;

import com.crud.config.GenericResponse;
import com.crud.dto.CompanyRequestDto;
import com.crud.dto.FetchCompany;
import com.crud.dto.LoginRequest;
import com.crud.entity.Address;
import com.crud.entity.Company;
import com.crud.entity.User;
import com.crud.exception.ExceptionHandler;
import com.crud.exception.NotFountException;
import com.crud.repository.AddressRepository;
import com.crud.repository.CompanyRepository;
import com.crud.repository.RoleRepository;
import com.crud.repository.UserRepository;
import com.crud.security.jwt.JwtTokenHelper;
import com.crud.service.CompanyService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    UserDetailsService userDetailsService;
    private DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();

    @Override
    public GenericResponse signUp(CompanyRequestDto companyRequestDto) {
        Company company = new Company(companyRequestDto.getCompanyName(), companyRequestDto.getPanCard(), companyRequestDto.getTelephoneNumber());
        User user = new User(companyRequestDto.getFirstName(), companyRequestDto.getLastName(), companyRequestDto.getEmail(), companyRequestDto.getUsername(), passwordEncoder.encode(companyRequestDto.getPassword()));
        Address address = new Address();
        address.setCity(companyRequestDto.getAddress().getCity());
        address.setPinCode(companyRequestDto.getAddress().getPinCode());
        address.setState(companyRequestDto.getAddress().getState());
        company.setAddress(addressRepository.save(address));
        user.setCompany(companyRepository.save(company));
        user.getRoles().add(roleRepository.getById(1L));
        user.getRoles().add(roleRepository.getById(3L));
        userRepository.save(user);

        return new GenericResponse(HttpStatus.OK, "Company added successfully", null, ExceptionHandler.MessageType.SUCCESS);
    }

    @Override
    public GenericResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (Exception e) {
            throw new NotFountException("UserName or Password invalid");
        }

        Map<String, Object> aMap = new HashMap<>();
        aMap.put("token", jwtTokenHelper.generateToken(userDetailsService.loadUserByUsername(loginRequest.getUsername())));
        aMap.put("user", userDetailsService.loadUserByUsername(loginRequest.getUsername()));
        return new GenericResponse(HttpStatus.OK, "Token Generated", aMap, ExceptionHandler.MessageType.SUCCESS);
    }

    @Override
    public GenericResponse update(Long companyId, CompanyRequestDto.UpDateCompany upDateCompany) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new NotFountException("company not found"));
        company.setCompanyName(upDateCompany.getCompanyName());
        companyRepository.saveAndFlush(company);
        return new GenericResponse(HttpStatus.OK, "Company update", null, ExceptionHandler.MessageType.SUCCESS);
    }

    @Override
    public GenericResponse fetchById(String token, Long companyId) {
        return new GenericResponse(HttpStatus.OK, "Fetch company by Id", dozerBeanMapper.map(companyRepository.findById(companyId).orElseThrow(() -> new NotFountException("company not found")), FetchCompany.class), ExceptionHandler.MessageType.INFO);
    }

    @Override
    public GenericResponse fetchAll() {
        return new GenericResponse(HttpStatus.OK, "Fetch All Company", companyRepository.findAllCompany(), ExceptionHandler.MessageType.SUCCESS);
    }

    @Override
    public GenericResponse saveUserAndAddress(Long userId, Set<Address> addresses) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFountException("user id not found"));
        user.getAssociatedAddress().addAll(addresses);
        userRepository.save(user);

        return new GenericResponse(HttpStatus.CREATED, "User And Address successfully Added", null, ExceptionHandler.MessageType.SUCCESS);
    }

    @Override
    public GenericResponse delete(Long companyId) {
        companyRepository.delete(companyRepository.getById(companyId));

        return new GenericResponse(HttpStatus.NOT_ACCEPTABLE, "Company delete", null, ExceptionHandler.MessageType.INFO);
    }
}
