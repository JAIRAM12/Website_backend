package com.example.My.website.backend.Security.service;

import com.example.My.website.backend.Dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;

    private final JWTService jwtService;

    public String verify(LoginRequest request) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        }
        return "Fail";
    }

}

