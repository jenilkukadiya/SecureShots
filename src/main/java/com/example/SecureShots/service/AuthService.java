package com.example.SecureShots.service;

import com.example.SecureShots.dto.AuthRequest;
import com.example.SecureShots.dto.AuthResponse;
import com.example.SecureShots.dto.SignupRequest;
import com.example.SecureShots.model.User;
import com.example.SecureShots.repository.UserRepository;
import com.example.SecureShots.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN") // or "USER"
                .build();
        userRepository.save(user);
        String token = jwtUtil.generateToken((UserDetails) user);
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken((UserDetails) user);
        return new AuthResponse(token);
    }
}
