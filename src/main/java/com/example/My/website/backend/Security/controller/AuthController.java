package com.example.My.website.backend.Security.controller;

import com.example.My.website.backend.Dto.LoginRequest;
import com.example.My.website.backend.Model.MongoPage;
import com.example.My.website.backend.Security.service.AuthService;
import com.example.My.website.backend.Security.service.MongoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@Component
@RequestMapping()
//@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final MongoUserDetailsService mongoUserDetailsService;

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.verify(loginRequest);
        if ("Fail".equals(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
        return ResponseEntity.ok(Map.of(
                "token", token));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        if (id.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid ID"));
        }

        return mongoUserDetailsService.getUserDetailsById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok) // specify type to avoid mismatch
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found")));
    }

    @GetMapping("/getPermission")
    public ResponseEntity<List<MongoPage>> getModels(){
        return ResponseEntity.ok(authService.getPermission());
    }
}