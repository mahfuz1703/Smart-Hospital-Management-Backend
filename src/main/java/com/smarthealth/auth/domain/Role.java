package com.smarthealth.auth.domain;

import com.smarthealth.auth.domain.enums.RoleName;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_name", columnNames = "name")
})
public class Role {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private RoleName name;

    public UUID getId(){
        return id;
    }

    public RoleName getName() {
        return name;
    }

    protected Role(){}
    public Role(RoleName name){
        this.name = name;
    }
}
