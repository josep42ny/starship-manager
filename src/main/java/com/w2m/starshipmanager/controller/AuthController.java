package com.w2m.starshipmanager.controller;

import com.w2m.starshipmanager.model.auth.LoginRequest;
import com.w2m.starshipmanager.model.auth.RegisterRequest;
import com.w2m.starshipmanager.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(token);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        String token = authService.register(request);

        return ResponseEntity.ok(token);
    }

}
