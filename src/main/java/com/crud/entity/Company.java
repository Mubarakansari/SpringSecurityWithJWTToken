package com.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long companyId;

    @NotBlank
    private String companyName;

    private String panCard;

    private String telephoneNumber;

    public Company(String companyName, String panCard, String telephoneNumber) {
        this.companyName = companyName;
        this.panCard = panCard;
        this.telephoneNumber = telephoneNumber;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "company")
    private Set<User> users = new HashSet<>();

}
