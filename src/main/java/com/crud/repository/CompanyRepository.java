package com.crud.repository;

import com.crud.dto.FetchCompany;
import com.crud.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT new com.crud.dto.FetchCompany(o.companyId, o.companyName, o.panCard, o.telephoneNumber, o.address.pinCode) FROM Company AS o ")
    List<FetchCompany> findAllCompany();
}
