package com.example.My.website.backend;

//package com.example.my.website.backend;

import com.example.My.website.backend.Service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyWebsiteBackEndApplicationTests {

    @Autowired
    private StaffService staffService;

    @Test
    void contextLoads() {
    }
}

