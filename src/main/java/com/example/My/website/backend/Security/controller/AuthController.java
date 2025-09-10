package com.example.My.website.backend.Security.controller;

import com.example.My.website.backend.Dto.LoginRequest;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.example.My.website.backend.Repo.StudentRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
import com.example.My.website.backend.Security.service.AuthService;
import com.example.My.website.backend.Security.service.MongoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@Component
@RequestMapping()
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final MongoUserDetailsService mongoUserDetailsService;

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.verify(loginRequest);
        if ("Fail".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
        return ResponseEntity.ok(mongoUserDetailsService.findUserDetails(loginRequest.getUsername(), token));
    }
}