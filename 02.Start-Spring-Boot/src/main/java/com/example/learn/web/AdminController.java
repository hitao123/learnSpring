package com.example.learn.web;


import com.example.learn.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AdminController {

    @GetMapping("/admin")
    public Object list() {

        return ResponseUtil.ok();
    }
}
