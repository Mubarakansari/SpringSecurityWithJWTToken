package com.crud.controller;

import com.crud.dto.CompanyRequestDto;
import com.crud.dto.LoginRequest;
import com.crud.dto.UserAndAddressDto;
import com.crud.entity.Address;
import com.crud.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Set;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping("/company/")
public class CompanyController {
    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody CompanyRequestDto companyRequest) {
        log.info("signUp");
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.signUp(companyRequest));
    }

    @PostMapping("signIn")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("signIn");
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.login(loginRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> companyUpdate(@RequestParam("companyId") Long companyId,  @Valid @RequestBody CompanyRequestDto.UpDateCompany upDateCompany) {
        log.info("companyUpdate");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(companyService.update(companyId, upDateCompany));
    }


    @GetMapping("fetchById/{companyId}")
    public ResponseEntity<?> fetchById(@RequestHeader("TOKEN") String token, @PathVariable Long companyId) {
        log.info("fetchById");
        return ResponseEntity.status(HttpStatus.OK).body(companyService.fetchById(token, companyId));
    }

    @PostMapping("saveUserAndAddress/{userId}")
    public ResponseEntity<?> saveUserAndCompany(@Valid @PathVariable Long userId, @RequestBody Set<Address> addresses) {
        log.info("saveUserAndCompany");

        return ResponseEntity.status(HttpStatus.OK).body(companyService.saveUserAndAddress(userId, addresses));
    }

    @GetMapping("fetchAll")
    public ResponseEntity<?> fetchAll() {
        log.info("fetchAll");
        return ResponseEntity.status(HttpStatus.OK).body(companyService.fetchAll());
    }

    @DeleteMapping("delete/{companyId}")
    public ResponseEntity<?> delete(@PathVariable Long companyId) {
        log.info("delete");
        return ResponseEntity.status(HttpStatus.OK).body(companyService.delete(companyId));
    }
}
