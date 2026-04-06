package com.smarthealth.auth.api.dto;

public record RegisterResponse(
        String message,
        String email,
        String role
) {}