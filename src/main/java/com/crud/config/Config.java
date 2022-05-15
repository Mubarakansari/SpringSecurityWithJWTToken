package com.crud.config;

import com.crud.entity.Role;
import com.crud.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class Config {
    @Autowired
    private final RoleRepository roleRepository;

    public Config(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void addRoles() {
        Role role1 = new Role("ADMIN");
        Role role2 = new Role("MANAGER");
        Role role3 = new Role("USER");
        Role role4 = new Role("EMPLOYEE");
        Role role5 = new Role("WATCHER");

        if (roleRepository.findAll().size() == 0) {
            roleRepository.saveAll(Arrays.asList(role1, role2, role3, role4, role5));
        }
    }
}
