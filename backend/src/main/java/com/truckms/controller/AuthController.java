package com.truckms.controller;

import com.truckms.dto.LoginRequest;
import com.truckms.dto.LoginResponse;
import com.truckms.entity.User;
import com.truckms.repository.UserRepository;
import com.truckms.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Username ama Password waa khalad");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Isticmaale lama helin"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());

        return ResponseEntity.ok(new LoginResponse(
                token, user.getUsername(), user.getFullName(), user.getRole().name()
        ));
    }
}
