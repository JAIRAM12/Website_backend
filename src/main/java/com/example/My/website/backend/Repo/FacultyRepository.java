package com.example.My.website.backend.Repo;

import com.example.My.website.backend.Model.MongoStaff;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends MongoRepository<MongoStaff, String> {
    Optional<MongoStaff> findByStaffId(String staffId);

}
