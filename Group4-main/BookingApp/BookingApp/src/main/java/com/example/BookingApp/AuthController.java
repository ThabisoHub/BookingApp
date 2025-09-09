package com.hospital.controller;

import com.hospital.model.User;
import com.hospital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authentication logic
        User user = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PatientRegistrationRequest registrationRequest) {
        // Registration logic
        try {
            User user = userService.registerPatient(registrationRequest);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Inner classes for request objects
    public static class LoginRequest {
        private String email;
        private String password;
        // Getters and setters
    }

    public static class PatientRegistrationRequest {
        private String fullName;
        private String email;
        private String phone;
        private String dateOfBirth;
        private String password;
        // Getters and setters
    }
}