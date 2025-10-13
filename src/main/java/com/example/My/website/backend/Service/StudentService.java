package com.example.My.website.backend.Service;

import com.example.My.website.backend.Dto.Studentdto;
import com.example.My.website.backend.Model.MongoStudent;
import com.example.My.website.backend.Repo.StudentRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class StudentService {

    private final StudentRepository repository;

    private final MongoTemplate mongoTemplate;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    public MongoStudent SaveStudent(Studentdto data) {
        MongoStudent student = modelMapper.map(data, MongoStudent.class);
        student.setId(null);
        student.setRole("Student");
        String encodedPassword = passwordEncoder.encode("12345");
        student.setPassword(encodedPassword);
        return repository.save(student);
    }

    public List<?> AddStudents(List<Studentdto> data) {
        try {
            if (data.size() == 1) {
                MongoStudent savedStudent = SaveStudent(data.getFirst());
                return Collections.singletonList(savedStudent);
            }

            return data.stream()
                    .map(this::SaveStudent)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error processing student data: {}", e.getMessage());
            return Collections.singletonList("Error processing student data: " + e.getMessage());
        }
    }

    public List<MongoStudent> searchStudent(JsonNode data){
            try {
            Query query = new Query();
                Iterator<Map.Entry<String, JsonNode>> fields = data.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    String key = entry.getKey();
                    String value = entry.getValue().asText();

                    if (!value.isEmpty()) {
                        query.addCriteria(Criteria.where(key).is(value));
                    }
                }

                return mongoTemplate.find(query, MongoStudent.class);

            } catch (Exception e) {
                e.printStackTrace();
                log.info("Studuent database is empty {}", data);
                return List.of();
            }
    }

}