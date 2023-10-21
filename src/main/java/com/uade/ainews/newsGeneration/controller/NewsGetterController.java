package com.uade.ainews.newsGeneration.controller;


import com.uade.ainews.newsGeneration.service.NewsGetterService;
import com.uade.ainews.newsGeneration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/news")
public class NewsGetterController {

    @Autowired
    private NewsGetterService newsGetterService;

    @Autowired
    private UserService userService;

    // Load the DB with news
    @PostMapping("/collectSources")
    public ResponseEntity<String> executeNews() {
        newsGetterService.getSameNews();
        return ResponseEntity.status(HttpStatus.OK).body("News batch executed successfully");
    }

    // Call user service to execute discount of interest process
    @PostMapping("/reduceInterest")
    public ResponseEntity<String> executeLackOfInterest() {
        userService.reduceUserInterest();
        return ResponseEntity.status(HttpStatus.OK).body("News batch executed successfully");
    }

    // Endpoint to test the connection
    @GetMapping("/test")
    public ResponseEntity<Object> login() {
        try {
            return ResponseEntity.ok("Application Up.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
