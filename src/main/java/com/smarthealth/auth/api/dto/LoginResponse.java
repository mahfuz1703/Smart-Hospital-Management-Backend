package com.smarthealth.auth.api.dto;

public record LoginResponse(
        String accessToken,
        String tokenType
) {}