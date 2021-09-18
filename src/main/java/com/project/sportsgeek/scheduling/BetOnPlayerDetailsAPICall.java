package com.project.sportsgeek.scheduling;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class BetOnPlayerDetailsAPICall {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Scheduled(fixedRate = 60000)
    public void scheduleTaskWithFixedRate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        System.out.println("Fixed Rate scheduler:: " + strDate);
        //Get Squad Details
//        https://cricapi.com/api/fantasySquad?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&unique_id=1254067
        //Get Fantasy Summary of the Match
//        https://cricapi.com/api/fantasySummary?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&unique_id=1254067
        //Api For Player Finder
//        https://cricapi.com/api/playerFinder?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&name=Rohit
        //APi For Player Statistics
//        https://cricapi.com/api/playerStats?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&pid=253802
        //Api FOr Live Cricket Score
//        https://cricapi.com/api/cricketScore?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&unique_id=1254067
        //Get Live Score With Detail
//        final String uri = "https://cricapi.com/api/fantasySummary?apikey=bUp8ZigBxgUzlwsaJyH4mQjIwC22&unique_id=1254067";
//
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject(uri, String.class);
//        System.out.println("Result:"+result);
//        int r = result.compareTo("winner_team");
//        System.out.println(r);
    }
}
