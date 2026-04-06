package com.smarthealth.auth.api;

import com.smarthealth.auth.api.dto.*;
import com.smarthealth.auth.service.RegistrationService;
import com.smarthealth.security.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final RegistrationService registrationService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtService.generateAccessToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<RegisterResponse> registerPatient(@Valid @RequestBody RegisterPatientRequest req) {
        return ResponseEntity.ok(registrationService.registerPatient(req));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<RegisterResponse> registerDoctor(@Valid @RequestBody RegisterDoctorRequest req) {
        return ResponseEntity.ok(registrationService.registerDoctor(req));
    }
}