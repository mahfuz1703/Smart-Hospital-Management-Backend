package com.smarthealth.auth.service;

import com.smarthealth.auth.domain.Role;
import com.smarthealth.auth.domain.enums.RoleName;
import com.smarthealth.auth.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class RoleSeeder implements ApplicationRunner {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args){
        Arrays.stream(RoleName.values()).forEach(roleName -> {
            roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(
                    new Role(roleName)));
        });
    }
}
