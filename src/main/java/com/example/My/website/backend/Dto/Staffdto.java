package com.example.My.website.backend.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class Staffdto {
//    private String id;
    private String name;
    private String department;
    private String email;
    private String phone;
    private List<String> education;
    private String staffId;
    private String image;
    private String password;
    private List<String> skills;
    private String address;
    private String position
    ;
}
