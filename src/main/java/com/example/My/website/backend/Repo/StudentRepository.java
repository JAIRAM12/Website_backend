package com.example.My.website.backend.Repo;

import com.example.My.website.backend.Model.MongoStudent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<MongoStudent, String> {
    Optional<MongoStudent> findByStudentId(String studentId);
}
