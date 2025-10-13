package com.example.My.website.backend.Security.service;

import com.example.My.website.backend.Dto.LoginRequest;
import com.example.My.website.backend.Model.MongoAdmin;
import com.example.My.website.backend.Model.MongoPage;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.PageRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authManager;

    private final JWTService jwtService;

    private final PageRespository pageRepo;

    public String verify(LoginRequest request) {
        log.info("Login attempt for username: {}", request.getUsername());

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String mongoId = null;
            String role = null;
            String avatarUrl = null;

            if (userDetails instanceof MongoStaff staff) {
                mongoId = staff.getId();
                role = "STAFF";
                avatarUrl = staff.getImage() != null ? "/api/staff/avatar/" + staff.getId() : null;
                log.info("User is STAFF with ID: {}", mongoId);
            } else if (userDetails instanceof MongoStudent student) {
                mongoId = student.getId();
                role = "STUDENT";
                avatarUrl = null;
                log.info("User is STUDENT with ID: {}", mongoId);
            } else if (userDetails instanceof MongoAdmin admin) {
                mongoId = admin.getId();
                role = "ADMIN";
                avatarUrl = admin.getImage() != null ? "/api/staff/avatar/" + admin.getId() : null;
                log.info("User is ADMIN with ID: {}", mongoId);
            }

            String token = jwtService.generateToken(userDetails, mongoId, role, avatarUrl);
            return token;

        } catch (Exception e) {
            log.error("Authentication failed for {}: {}", request.getUsername(), e.getMessage(), e);
            return "Fail";
        }
    }

    public List<MongoPage> getPermission() {
        return pageRepo.findAll();
    }
}

