package com.smarthealth.doctor.domain;

import com.smarthealth.auth.domain.User;
import com.smarthealth.common.domain.BaseEntity;
import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "doctors", uniqueConstraints = {
        @UniqueConstraint(name = "uk_doctors_user_id", columnNames = "user_id"),
        @UniqueConstraint(name = "uk_doctors_license_number", columnNames = "license_number")
})
public class Doctor extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_doctors_user"))
    private User user;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, length = 80)
    private String specialization;

    @Column(name = "license_number", length = 50)
    private String licenseNumber;

    @Column(nullable = false)
    private int yearsOfExperience = 0;

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }
    public String getSpecialization() { return specialization; }

    protected Doctor() {}

    public Doctor(User user, String fullName, String specialization) {
        this.user = user;
        this.fullName = fullName;
        this.specialization = specialization;
    }
}