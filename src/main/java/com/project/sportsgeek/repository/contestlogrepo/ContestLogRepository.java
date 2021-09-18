package com.project.sportsgeek.repository.contestlogrepo;

import com.project.sportsgeek.model.ContestLog;
import com.project.sportsgeek.model.ContestLogFormatted;
import com.project.sportsgeek.model.ContestLogWithUser;
import com.project.sportsgeek.model.PublicChatFormatted;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "contestLogRepo")
public interface ContestLogRepository {

    int addContestLog(ContestLog contestLog) throws Exception;

    List<ContestLogWithUser> findAllContestLogForLastDays(int days);
    List<ContestLogWithUser> findAllContestLogAfterId(int contestLogId);

    List<ContestLogFormatted> findAllContestLogFormattedForLastDays(int days);
    List<ContestLogFormatted> findAllContestLogFormattedAfterId(int contestLogId);

    boolean deleteContestLogByUserId(int userId) throws Exception;
}
