package com.uade.ainews.newsGeneration.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronService {
//Every 10 seconds: "*/10 * * * * *"
//Every hour between 8AM to 1AM: "0 0 8-1 ? * * *"
    @Scheduled (cron = "${cron.expression}")
    public void cronTest(){
        long now = System.currentTimeMillis()/1000;
        System.out.println("Scheduled task using cron job - " + now);
    }
}
