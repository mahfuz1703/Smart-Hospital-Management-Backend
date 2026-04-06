package com.smarthealth.common.api;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class MeController {

    @GetMapping("/me")
    public Object me(Authentication authentication) {
        return authentication;
    }
}