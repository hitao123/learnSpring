package com.example.learn.web;


import com.example.learn.util.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdController {

    @PostMapping("/ad")
    public Object list() {
        return ResponseUtil.ok();
    }
}
