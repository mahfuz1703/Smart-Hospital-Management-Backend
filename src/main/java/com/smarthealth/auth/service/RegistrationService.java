package com.smarthealth.auth.service;

import com.smarthealth.auth.api.dto.RegisterDoctorRequest;
import com.smarthealth.auth.api.dto.RegisterPatientRequest;
import com.smarthealth.auth.api.dto.RegisterResponse;
import com.smarthealth.auth.domain.User;
import com.smarthealth.auth.domain.enums.RoleName;
import com.smarthealth.auth.repository.RoleRepository;
import com.smarthealth.auth.repository.UserRepository;
import com.smarthealth.doctor.domain.Doctor;
import com.smarthealth.doctor.repository.DoctorRepository;
import com.smarthealth.patient.domain.Patient;
import com.smarthealth.patient.repository.PatientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public RegistrationService(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder,
                               PatientRepository patientRepository,
                               DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public RegisterResponse registerPatient(RegisterPatientRequest req) {
        String email = normalizeEmail(req.email());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        var role = roleRepository.findByName(RoleName.PATIENT)
                .orElseThrow(() -> new IllegalStateException("Role not found in DB: PATIENT"));

        User user = new User(email, passwordEncoder.encode(req.password()));
        user.getRoles().add(role);
        user = userRepository.save(user);

        patientRepository.save(new Patient(user, req.fullName()));

        return new RegisterResponse("created", email, "ROLE_PATIENT");
    }

    @Transactional
    public RegisterResponse registerDoctor(RegisterDoctorRequest req) {
        String email = normalizeEmail(req.email());

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered: " + email);
        }

        var role = roleRepository.findByName(RoleName.DOCTOR)
                .orElseThrow(() -> new IllegalStateException("Role not found in DB: DOCTOR"));

        User user = new User(email, passwordEncoder.encode(req.password()));
        user.getRoles().add(role);
        user = userRepository.save(user);

        doctorRepository.save(new Doctor(user, req.fullName(), req.specialization()));

        return new RegisterResponse("created", email, "ROLE_DOCTOR");
    }

    private static String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }
}