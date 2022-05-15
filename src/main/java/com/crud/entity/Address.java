package com.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String pinCode;

    private String state;

    private String city;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "userAndAddressMapping", joinColumns = @JoinColumn(name = "address_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> associatedUser = new HashSet<>();
}
