package com.uade.ainews.newsGeneration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronService {


    @Autowired
    NewsGetterService newsGetterService;

    @Autowired
    UserService userService;

    // Cron execution is currently disallowed. Execution is controlled by endpoints
    //@Scheduled (cron = "@weekly")
    //@Scheduled(cron = "@hourly")
    public void cronTest() {
        newsGetterService.getSameNews();
        userService.reduceUserInterest();
        long now = System.currentTimeMillis() / 1000;
        System.out.println("Scheduled task using cron job - " + now);
    }
}
