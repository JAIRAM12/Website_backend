package com.example.My.website.backend.controller;
import com.example.My.website.backend.Dto.Staffdto;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.example.My.website.backend.Service.StaffService;
import com.example.My.website.backend.Service.StudentService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
public class StaffAPI {
    private final FacultyRepository facultyRepository;
    private final StaffService staffService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<MongoStaff> addFaculty(@RequestBody Staffdto staffInfo) {
        return ResponseEntity.ok(staffService.AddStaff(staffInfo));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        facultyRepository.deleteById(id);
    }


    @PostMapping("/Faculties")
    public ResponseEntity<List<MongoStaff>> fetchFaculty(@RequestBody JsonNode data) {
        List<MongoStaff> staffList = staffService.getStaff(data);
        return ResponseEntity.ok(staffList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MongoStaff>> getAllStaff() {
        List<MongoStaff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MongoStaff> getStaff(@PathVariable String id) {
        MongoStaff staffInfo = staffService.getStaffById(id);
        return ResponseEntity.ok(staffInfo);
    }

    @PostMapping("/search/meenties")
    public ResponseEntity<List<MongoStudent>> getMeenties(@RequestBody JsonNode data){
        List<MongoStudent> Meenties = studentService.searchStudent(data);
        return ResponseEntity.ok(Meenties);
    }


}

