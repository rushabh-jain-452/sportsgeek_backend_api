package com.project.sportsgeek.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GetLiveScoreFromAPI {
    @Scheduled(fixedRate = 60000)
    public void scheduleTaskWithFixedRate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        System.out.println("Fixed Rate scheduler:: " + strDate);
//        //Get Live Score From the API
//        final String uri = "https://cricapi.com/api/cricketScore?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&unique_id=1254067";
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        System.out.println("Result:" + result);
    }
}
