package com.example.My.website.backend.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Staff database")
@Getter
@Setter
public class MongoStaff {

    @Id
    private String id;
    private String name;
    private String department;
    private String email;
    private String phone;
    private String education;
    private String staffId;
    private Binary staffImage;
    private String pass;


}
