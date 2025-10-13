package com.example.My.website.backend.controller;

import com.example.My.website.backend.Dto.Admindto;
import com.example.My.website.backend.Model.MongoAdmin;
import com.example.My.website.backend.Repo.AdminRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminAPI {
    private final AdminRepo adminRepo;

    @PostMapping("/{id}")
    public ResponseEntity<Optional<MongoAdmin>> getAdmin(@PathVariable String id) {
        return ResponseEntity.ok(adminRepo.findById(id));
    }


}
