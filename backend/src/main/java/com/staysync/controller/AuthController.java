package com.staysync.controller;

import com.staysync.config.JwtTokenProvider;
import com.staysync.dto.AuthRequest;
import com.staysync.dto.AuthResponse;
import com.staysync.model.Role;
import com.staysync.model.User;
import com.staysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        // Check password (supports BCrypt or plain demo match for convenience)
        if (user == null || (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())
                && !request.getPassword().equals("password123")
                && !request.getPassword().equals(user.getPasswordHash()))) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }

        String token = tokenProvider.generateToken(user);
        AuthResponse response = new AuthResponse(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getRoomNumber(),
                user.getBedNumber()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already in use"));
        }

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash() != null ? user.getPasswordHash() : "password123"));
        if (user.getRole() == null) {
            user.setRole(Role.STUDENT);
        }
        User saved = userRepository.save(user);

        String token = tokenProvider.generateToken(saved);
        return ResponseEntity.ok(new AuthResponse(
                token, saved.getId(), saved.getName(), saved.getEmail(), saved.getRole(), saved.getRoomNumber(), saved.getBedNumber()
        ));
    }

    @GetMapping("/demo-accounts")
    public ResponseEntity<?> getDemoAccounts() {
        return ResponseEntity.ok(List.of(
                Map.of("role", "STUDENT", "name", "Alex Rivers", "email", "alex.student@staysync.edu", "room", "B-204"),
                Map.of("role", "WARDEN", "name", "Dr. Robert Sharma", "email", "warden@staysync.edu", "room", "Admin Block"),
                Map.of("role", "ADMINISTRATOR", "name", "Dean Eleanor Vance", "email", "admin@staysync.edu", "room", "Head Office"),
                Map.of("role", "MAINTENANCE", "name", "Marcus Fixer", "email", "maintenance@staysync.edu", "room", "Workshop 1"),
                Map.of("role", "SECURITY", "name", "Officer Vikram Khan", "email", "security@staysync.edu", "room", "Main Gate")
        ));
    }
}
