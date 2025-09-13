package com.example.My.website.backend.Security.service;

import com.example.My.website.backend.Dto.LoginRequest;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Model.MongoStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;

    private final JWTService jwtService;

//    public String verify(LoginRequest request) {
//        Authentication authentication =
//                authManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                                request.getUsername(),
//                                request.getPassword()
//                        )
//                );
//
//        if (authentication.isAuthenticated()) {
//            return jwtService.generateToken(request.getUsername());
//        }
//        return "Fail";
//    }

    public String verify(LoginRequest request) {
        Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        if (authentication.isAuthenticated()) {
            // Get authenticated user (MongoStaff or MongoStudent)
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String mongoId = null;
            String role = null;
            String avatarUrl = null;

            if (userDetails instanceof MongoStaff staff) {
                mongoId = staff.getId();
                role = "STAFF"; // or staff.getRole() if you have a role field
                avatarUrl = staff.getStaffImage() != null ? "/api/staff/avatar/" + staff.getId() : null;
            } else if (userDetails instanceof MongoStudent student) {
                mongoId = student.getId();
                role = "STUDENT";
                avatarUrl = null;
            }

            // ✅ Use new generateToken with claims
            return jwtService.generateToken(userDetails, mongoId, role, avatarUrl);
        }
        return "Fail";
    }


}

