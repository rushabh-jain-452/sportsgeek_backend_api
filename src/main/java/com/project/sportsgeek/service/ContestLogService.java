package com.project.sportsgeek.service;

import com.project.sportsgeek.model.ContestLogFormatted;
import com.project.sportsgeek.model.ContestLogWithUser;
import com.project.sportsgeek.repository.contestlogrepo.ContestLogRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestLogService {
    @Autowired
    @Qualifier(value = "contestLogRepo")
    ContestLogRepository contestLogRepository;

    public Result<List<ContestLogWithUser>> findAllContestLogForLastDays(int days) {
        List<ContestLogWithUser> contestLogList = contestLogRepository.findAllContestLogForLastDays(days);
        return new Result<>(200, contestLogList);
    }

    public Result<List<ContestLogWithUser>> findAllContestLogAfterId(int contestLogId) {
        List<ContestLogWithUser> contestLogList = contestLogRepository.findAllContestLogAfterId(contestLogId);
        return new Result<>(200, contestLogList);
    }

    public Result<List<ContestLogFormatted>> findAllContestLogFormattedForLastDays(int days) {
        List<ContestLogFormatted> contestLogList = contestLogRepository.findAllContestLogFormattedForLastDays(days);
        return new Result<>(200, contestLogList);
    }

    public Result<List<ContestLogFormatted>> findAllContestLogFormattedAfterId(int contestLogId) {
        List<ContestLogFormatted> contestLogList = contestLogRepository.findAllContestLogFormattedAfterId(contestLogId);
        return new Result<>(200, contestLogList);
    }

}
