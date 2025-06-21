package com.example.SecureShots.controller;

import com.example.SecureShots.model.User;
import com.example.SecureShots.repository.UserRepository;
import com.example.SecureShots.service.AuthService;
import com.example.SecureShots.dto.AuthResponse;
import com.example.SecureShots.dto.LoginRequest;
import com.example.SecureShots.dto.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email is already in use");
        }

        User user = authService.registerUser(request);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.authenticateUser(
                request.getEmail(),
                request.getPassword(),
                authenticationManager
        );
        return ResponseEntity.ok(new AuthResponse(token, request.getEmail()));
    }
}
