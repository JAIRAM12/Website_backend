package com.example.My.website.backend.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Studentdto {

    private String name;
    private String phone;
    private String department;
    private String studentId;
    private int year;
    private String gender;
    private String createdBy;
    private String password;
}
