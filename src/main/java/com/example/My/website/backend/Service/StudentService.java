package com.example.My.website.backend.Service;

import com.example.My.website.backend.Dto.Studentdto;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    private final ObjectMapper objectMapper;

    private final MongoTemplate mongoTemplate;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    public MongoStudent SaveStudent(Studentdto data) {
        MongoStudent student = modelMapper.map(data, MongoStudent.class);
        student.setId(null);
        String encodedPassword = passwordEncoder.encode(data.getPassword());
        student.setPassword(encodedPassword);
        return repository.save(student);
    }

    public List<?> AddStudents(List<Studentdto> data) {
        try {
            // If it's a single object wrapped in array
            if (data.size() == 1) {
                MongoStudent savedStudent = SaveStudent(data.get(0)); // ✅ Fixed
                return Collections.singletonList(savedStudent);
            }

            // If it's multiple objects
            List<MongoStudent> savedStudents = data.stream()
                    .map(this::SaveStudent) // ✅ Fixed
                    .collect(Collectors.toList());

            return savedStudents; // ✅ Added return
        } catch (Exception e) {
            return Collections.singletonList("Error processing student data: " + e.getMessage());
        }
    }

    public List<MongoStudent> searchStudent(JsonNode data){
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

                return mongoTemplate.find(query, MongoStudent.class);

            } catch (Exception e) {
                e.printStackTrace();
                return List.of(); // return empty list on error
            }
    }

}