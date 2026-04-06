package com.smarthealth.auth.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterDoctorRequest(
        @Email @NotBlank String email,
        @NotBlank @Size(min = 6, max = 72) String password,
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Size(max = 80) String specialization
) {}