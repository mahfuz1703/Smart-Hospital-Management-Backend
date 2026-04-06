package com.smarthealth.auth.repository;

import com.smarthealth.auth.domain.Role;
import com.smarthealth.auth.domain.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(RoleName name);
}
