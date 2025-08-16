//package controller;
//
//import Dto.com.example.my.website.backend.Staffdto;
//import Model.com.example.my.website.backend.MongoStaff;
//import Repo.com.example.my.website.backend.FacultyRepository;
//import Service.com.example.my.website.backend.StaffService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/faculty")
//@RequiredArgsConstructor
//public class StaffAPI {
//
//    private final FacultyRepository facultyRepository;
//    private final StaffService staffService;
//
////    @GetMapping
////    public List<StaffInfo> getAll() {
////        return facultyRepository.findAll();
////    }
//
//    @PostMapping
//    public MongoStaff create(@RequestBody Staffdto faculty) {
//        return staffService.AddStaff(faculty);
//    }
//
////    @PutMapping("/{id}")
////    public StaffInfo update(@PathVariable String id, @RequestBody StaffInfo faculty) {
////        faculty.setId(id);
////        return facultyRepository.save(faculty);
////    }
//
//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable String id) {
//        facultyRepository.deleteById(id);
//    }
//}

//package com.example.My.website.Back_end.controller;
package com.example.My.website.backend.controller;
import com.example.My.website.backend.Dto.Staffdto;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.example.My.website.backend.Service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/faculty")
@RequiredArgsConstructor
@CrossOrigin
public class StaffAPI {
    private final FacultyRepository facultyRepository;
    private final StaffService staffService;

    @PostMapping
//    @PostMapping
    public ResponseEntity<String> addFaculty(@RequestBody Staffdto staffdto) {
        try {
            MongoStaff saved = staffService.AddStaff(staffdto);
            return ResponseEntity.ok().body("✅ Staff saved successfully with ID: " + saved.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("❌ Invalid Base64 image data");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to save staff");
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        facultyRepository.deleteById(id);
    }
}

