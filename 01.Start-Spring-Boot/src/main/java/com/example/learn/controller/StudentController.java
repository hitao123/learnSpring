package com.example.learn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.learn.entity.*;

@RestController
public class StudentController {

    @Autowired
    private  Student student;

    @RequestMapping("/")
    String index() {
        return student.getSno() + student.getSex() + student.getName();
    }
}
