package com.project.sportsgeek.repository.contestrepo;

import com.project.sportsgeek.model.Contest;
import com.project.sportsgeek.model.ContestWithResult;
import com.project.sportsgeek.model.ContestWithUser;
import com.project.sportsgeek.model.Gender;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "contestRepo")
public interface ContestRepository {

    Contest findContestByUserAndMatch(int userId, int matchId) throws Exception;

    Contest findContestById(int contestId) throws Exception;

    List<ContestWithUser> findAllContestByMatchId(int matchId) throws Exception;

    List<ContestWithResult> findContestResultByMatchId(int matchId) throws Exception;

    int addContest(Contest contest) throws Exception;

    boolean updateContest(int contestId, Contest contest) throws Exception;

    int getContestPoints(int contestId) throws Exception;

    boolean deleteContestById(int contestId) throws Exception;

    boolean deleteContestsByUserId(int userId) throws Exception;

}