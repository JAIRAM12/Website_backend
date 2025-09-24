package com.example.My.website.backend.Service;

import com.example.My.website.backend.Dto.Staffdto;
import com.example.My.website.backend.Model.MongoStaff;
import com.example.My.website.backend.Repo.FacultyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final FacultyRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final MongoTemplate mongoTemplate;

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
        faculty.setPassword(passwordEncoder.encode("12345"));
        faculty.setDepartment(staffInfo.getDepartment());
        faculty.setEducation(staffInfo.getEducation());
        faculty.setPhone(staffInfo.getPhone());
        faculty.setAddress(staffInfo.getAddress());
        faculty.setPosition(staffInfo.getPosition());
        faculty.setRole("Staff");
        faculty.setSkills(staffInfo.getSkills());

        // Decode Base64 image if provided
        if (staffInfo.getImage() != null && !staffInfo.getImage().isEmpty()) {
            try {
                String base64Image = staffInfo.getImage();
                if (base64Image.contains(",")) {
                    base64Image = base64Image.split(",")[1];
                }
                byte[] imageBytes = Base64.getDecoder().decode(base64Image);
                faculty.setImage(new Binary(imageBytes));
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid Base64 image data", e);
            }
        }

        // Save and return
        return repository.save(faculty);
    }


    public List<MongoStaff> getStaff(JsonNode data) {
        try {
//            JsonNode node = objectMapper.readTree(jsonData);
            Query query = new Query();

            // Iterate over all fields dynamically
            Iterator<Map.Entry<String, JsonNode>> fields = data.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String key = entry.getKey();
                String value = entry.getValue().asText();

                if (!value.isEmpty()) { // ignore empty fields
                    query.addCriteria(Criteria.where(key).is(value));
                }
            }

            return mongoTemplate.find(query, MongoStaff.class);

        } catch (Exception e) {
            e.printStackTrace();
            return List.of(); // return empty list on error
        }
    }

    public List<MongoStaff> getAllStaff() {
        return repository.findAll();
    }

    public MongoStaff getStaffById(String id) {
        System.out.println(repository.findById(id).orElse(null));
        return repository.findById(id).orElse(null);
    }


}
