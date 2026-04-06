package com.smarthealth.patient.repository;

import com.smarthealth.patient.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByUser_Email(String email);
    boolean existsByUser_Email(String email);
}