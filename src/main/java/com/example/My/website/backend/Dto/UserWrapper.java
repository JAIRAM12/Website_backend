package com.example.My.website.backend.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserWrapper {
    private String role;
    private Object user;
}
