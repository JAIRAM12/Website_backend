package com.example.My.website.backend.Service;

import com.example.My.website.backend.Dto.Staffdto;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Repo.FacultyRepository;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class StaffService {
    private final FacultyRepository repository;
    public StaffService(FacultyRepository repository) {
        this.repository = repository;
    }

    public MongoStaff AddStaff(Staffdto staffInfo) {
        MongoStaff faculty = new MongoStaff();
        faculty.setName(staffInfo.getName());
        faculty.setStaffId(staffInfo.getStaffId());
        faculty.setEmail(staffInfo.getEmail());
        faculty.setPass(staffInfo.getPass());
        faculty.setDepartment(staffInfo.getDepartment());
        faculty.setEducation(staffInfo.getEducation());
        faculty.setPhone(staffInfo.getPhone());


        if (staffInfo.getImage() != null && !staffInfo.getImage().isEmpty()) {
            try {
                String base64Image = staffInfo.getImage();

                // If the string starts with "data:image/...", strip it
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }

                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                faculty.setStaffImage(new Binary(imageBytes));
//                staffInfo.setImage();
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }

        return repository.save(faculty);
    }



}
