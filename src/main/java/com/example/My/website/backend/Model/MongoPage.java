package com.example.My.website.backend.Model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "page_permission")
@Getter
@Setter
public class MongoPage {

    @Id
    private String id;
    private String roles;
    private String path;
    private String component;
    private boolean isProtected;
}
