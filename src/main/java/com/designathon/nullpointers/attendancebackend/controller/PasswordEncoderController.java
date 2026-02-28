package com.designathon.nullpointers.attendancebackend.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PasswordEncoderController {
    
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    @GetMapping("/api/encode-password")
    public Map<String, String> encodePassword(@RequestParam String password) {
        return Map.of(
            "original", password,
            "encoded", encoder.encode(password)
        );
    }
}
