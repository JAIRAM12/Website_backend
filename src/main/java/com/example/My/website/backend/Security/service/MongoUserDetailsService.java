package com.example.My.website.backend.Security.service;

import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.example.My.website.backend.Repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoUserDetailsService implements UserDetailsService {

    private final FacultyRepository staffRepository;
    private final StudentRepository studentRepository;


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        // Check if user is a staff member using Optional
        Optional<MongoStaff> staffOptional = staffRepository.findByStaffId(identifier);
        if (staffOptional.isPresent()) {
            return staffOptional.get();
        }

        // Check if user is a student using Optional
        Optional<MongoStudent> studentOptional = studentRepository.findByStudentId(identifier);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }

        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }

    public Map<String, Object> findUserDetails(String identifier, String jwtToken) {
        UserDetails userInfo = loadUserByUsername(identifier);

        String role = userInfo.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("USER");

        Binary avatar = null;

        if (userInfo instanceof MongoStaff staff) {
            avatar = staff.getStaffImage();  // assuming you have this field
        } else if (userInfo instanceof MongoStudent student) {
//            avatar = student.get; // assuming you have this field
        }

        return Map.of(
                "token", jwtToken,
                "user", Map.of(
                        "id", userInfo.getUsername(),
                        "role", role,
                        "avatar", avatar
                )
        );
    }
}