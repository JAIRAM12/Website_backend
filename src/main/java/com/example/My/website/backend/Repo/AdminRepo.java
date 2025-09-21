package com.example.My.website.backend.Repo;

import com.example.My.website.backend.Model.MongoAdmin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends MongoRepository<MongoAdmin, String> {
    Optional<MongoAdmin> findByAdminId(String adminId);
}
