package com.example.My.website.backend.controller;

import com.example.My.website.backend.Dto.Studentdto;
import com.example.My.website.backend.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentAPI {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addFaculty(@RequestBody List<Studentdto> studentInfo) {
        try {
            if (studentInfo.isEmpty()) {
                return ResponseEntity.badRequest().body("No student data provided");
            }

            return ResponseEntity.ok(studentService.AddStudents(studentInfo));

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing student data: " + e.getMessage());
        }
    }
}
