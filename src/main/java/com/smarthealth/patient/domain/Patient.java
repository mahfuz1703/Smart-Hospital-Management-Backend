package com.smarthealth.patient.domain;

import com.smarthealth.auth.domain.User;
import com.smarthealth.common.domain.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(name = "uk_patients_user_id", columnNames = "user_id")
})
public class Patient extends BaseEntity {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "uuid")
    private UUID id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_patients_user"))
    private User user;

    @Column(nullable = false, length = 120)
    private String fullName;

    private LocalDate dateOfBirth;

    @Column(length = 30)
    private String phone;

    @Column(length = 255)
    private String address;

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }

    protected Patient() {}

    public Patient(User user, String fullName) {
        this.user = user;
        this.fullName = fullName;
    }
}