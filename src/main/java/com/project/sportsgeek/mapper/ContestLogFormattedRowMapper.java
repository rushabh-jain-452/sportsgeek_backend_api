package com.project.sportsgeek.mapper;

import com.project.sportsgeek.model.ContestLogFormatted;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ContestLogFormattedRowMapper implements RowMapper<ContestLogFormatted> {
    @Override
    public ContestLogFormatted mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContestLogFormatted contestLogFormatted = new ContestLogFormatted();
        contestLogFormatted.set_id("c" + rs.getInt("ContestLogId"));
        contestLogFormatted.setCreatedAt(rs.getTimestamp("LogTimestamp"));
        contestLogFormatted.setSystem(true);
        // Generating message
        Timestamp matchStartDatetime = rs.getTimestamp("StartDatetime");
        String team1Name = rs.getString("Team1Name");
        String team2Name = rs.getString("Team2Name");
        String newTeamName = rs.getInt("NewTeamId") == rs.getInt("Team1") ? team1Name : team2Name;
        String msg = team1Name + " VS " + team2Name + " " + getFormattedDate(matchStartDatetime);
        msg += " -> " + rs.getString("FirstName") + " " + rs.getString("LastName");
        if(rs.getString("Action").equals("INSERT")){
            msg += " placed bet on " + newTeamName + " for " + rs.getInt("NewContestPoints") + " points";
        }
        else if(rs.getString("Action").equals("UPDATE")){
            String oldTeamName = rs.getInt("OldTeamId") == rs.getInt("Team1") ? rs.getString("Team1Name") : rs.getString("Team2Name");
            int oldPoints = rs.getInt("OldContestPoints");
            int newPoints = rs.getInt("NewContestPoints");
            msg += " changed ";
            if(!newTeamName.equals(oldTeamName) && newPoints != oldPoints){
                msg += "Bet from " + oldTeamName + " to " + newTeamName + " from " + oldPoints + " to " + newPoints + " points";
            }
            else if(newPoints != oldPoints){
                msg += "Points from " + oldPoints + " to " + newPoints + " for " + newTeamName;
            }
            else if(!newTeamName.equals(oldTeamName)){
                msg += "Team from " + oldTeamName + " to " + newTeamName + " for " + newPoints + " points";
            }
        }
        msg +=  " at " + getFormattedDate(rs.getTimestamp("LogTimestamp"));
        contestLogFormatted.setText(msg);
        return contestLogFormatted;
    }

    private String getFormattedDate(Timestamp timestamp){
//      0123456789
//      2021-07-14 12:47:48.0
        String str = timestamp.toString();
        String day = str.substring(8,10);
        String mth = str.substring(5,7);
        String yr = str.substring(0,4);
        int hr = Integer.parseInt(str.substring(11,13));
        String min = str.substring(14,16);
        String ampm = "AM";
//        if(hr <= 12){
//            ampm = "AM";
//        }
//        else{
//            ampm = "PM";
//            hr -= 12;
//        }
        if(hr >= 12){
            ampm = "PM";
        }
        if(hr > 12){
            hr -= 12;
        }
        return day + "-" + mth + "-" + yr + " " + hr + ":" + min + " " + ampm;
    }
}
