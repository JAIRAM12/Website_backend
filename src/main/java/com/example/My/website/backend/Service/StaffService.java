package com.example.My.website.backend.Service;

import com.example.My.website.backend.Dto.Staffdto;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Repo.FacultyRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    private final FacultyRepository repository;
    public StaffService(FacultyRepository repository) {
        this.repository = repository;
    }
    public MongoStaff AddStaff(Staffdto staffInfo) {
        // Check if staffId already exists
        Optional<MongoStaff> existingData = repository.findByStaffId(staffInfo.getStaffId());
        if (existingData.isPresent()) {
            throw new RuntimeException("This id already exists: " + staffInfo.getStaffId());
        }

        // Map DTO to entity
        MongoStaff faculty = new MongoStaff();
        faculty.setName(staffInfo.getName());
        faculty.setStaffId(staffInfo.getStaffId());
        faculty.setEmail(staffInfo.getEmail());
        faculty.setPass(staffInfo.getPass());
        faculty.setDepartment(staffInfo.getDepartment());
        faculty.setEducation(staffInfo.getEducation());
        faculty.setPhone(staffInfo.getPhone());

        // Decode Base64 image if provided
        if (staffInfo.getImage() != null && !staffInfo.getImage().isEmpty()) {
            try {
                String base64Image = staffInfo.getImage();
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                faculty.setStaffImage(new Binary(imageBytes));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }

        // Save and return
        return repository.save(faculty);
    }


    public List<MongoStaff> getStaff(){
        return repository.findAll();
    }



}
