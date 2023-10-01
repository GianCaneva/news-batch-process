package com.uade.ainews.newsGeneration.controller;


import com.uade.ainews.newsGeneration.service.NewsGetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/news")
public class NewsGetterController {

    @Autowired
    private NewsGetterService newsGetterService;

    @PostMapping("/collectSources")
    public ResponseEntity<String> executeNews() throws IOException {
        newsGetterService.getSameNews();
        return ResponseEntity.status(HttpStatus.OK).body("News batch executed successfully");
    }

}
