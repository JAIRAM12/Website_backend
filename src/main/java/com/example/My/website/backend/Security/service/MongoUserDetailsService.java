package com.example.My.website.backend.Security.service;

import com.example.My.website.backend.Dto.UserWrapper;
import com.example.My.website.backend.Model.MongoAdmin;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.AdminRepo;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.example.My.website.backend.Repo.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MongoUserDetailsService implements UserDetailsService {

    private final FacultyRepository staffRepository;
    private final StudentRepository studentRepository;
    private final AdminRepo adminRepo;


    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        Optional<MongoAdmin> adminOptional = adminRepo.findByAdminId(identifier);
        if (adminOptional.isPresent()) {
            return adminOptional.get();
        }

        Optional<MongoStaff> staffOptional = staffRepository.findByStaffId(identifier);
        if (staffOptional.isPresent()) {
            return staffOptional.get();
        }

        Optional<MongoStudent> studentOptional = studentRepository.findByStudentId(identifier);
        if (studentOptional.isPresent()) {
            return studentOptional.get();
        }

        log.error("User not found with identifier: {}", identifier);
        throw new UsernameNotFoundException("User not found with identifier: " + identifier);
    }

    public Optional<UserWrapper> getUserDetailsById(String id) {
        MongoAdmin admin1 = adminRepo.findById(id).orElse(null);
        return adminRepo.findById(id)
                .map(admin -> new UserWrapper("ADMIN", admin))
                .or(() -> staffRepository.findById(id)
                        .map(staff -> new UserWrapper("STAFF", staff)))
                .or(() -> studentRepository.findById(id)
                        .map(student -> new UserWrapper("STUDENT", student)));
    }

}