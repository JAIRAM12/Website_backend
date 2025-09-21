package com.example.My.website.backend.Repo;

import com.example.My.website.backend.Model.MongoPage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PageRespository extends MongoRepository<MongoPage, String> {
}
