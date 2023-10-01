package com.uade.ainews.newsGeneration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronService {
//Every 10 seconds: "*/10 * * * * *"
//Every hour between 8AM to 1AM: "0 0 8-1 ? * * *"

    @Autowired
    NewsGetterService newsGetterService;
//    @Scheduled (cron = "@weekly")
    @Scheduled (cron = "@hourly")
    public void cronTest(){
        //newsGetterService.getSameNews();
        long now = System.currentTimeMillis()/1000;
        System.out.println("Scheduled task using cron job - " + now);
    }
}
