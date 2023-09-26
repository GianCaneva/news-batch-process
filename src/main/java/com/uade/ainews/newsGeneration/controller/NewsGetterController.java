package com.uade.ainews.newsGeneration.controller;


import com.uade.ainews.newsGeneration.service.NewsGetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsGetterController {

    @Autowired
    private NewsGetterService newsGetterService;

    /*
    @GetMapping("/all")
    public ResponseEntity<?> getNews() {

        Optional<News> news = newsGetterService.test();
        if (news.isPresent()) {
            return ResponseEntity.ok(news.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"No news found\"}");
        }
    }
    */


    @PostMapping("/execute")
    public ResponseEntity<String> executeNews() {
        newsGetterService.getSameNews();
        return ResponseEntity.status(HttpStatus.OK).body("News batch executed successfully");
    }

}
