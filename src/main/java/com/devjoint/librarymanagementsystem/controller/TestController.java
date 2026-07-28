package com.devjoint.librarymanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/user")
    public String userEndpoint() {
        return "Welcome USER!";
    }

    @GetMapping("/api/admin")
    public String adminEndpoint() {
        return "Welcome ADMIN!";
    }

    @GetMapping("/api/common")
    public String commonEndpoint() {
        return "Accessible by USER and ADMIN";
    }
}