package com.project.sportsgeek.repository.playerrepo;

import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerRepo")
public interface PlayerRepository {

    List<PlayerResponse> findAllPlayers();

    PlayerResponse findPlayerByPlayerId(int playerId) throws Exception;

    List<PlayerResponse> findPlayerByPlayerType(int playerTypeId) throws Exception;

    List<PlayerResponse> findPlayerByTeamId(int teamId) throws Exception;

    int addPlayer(Player player) throws Exception;

    boolean updatePlayer(int playerId, Player player) throws Exception;

    boolean updatePlayerWithProfilePicture(int playerId, Player player) throws Exception;

    boolean updatePlayerType(int playerId, int playerTypeId) throws Exception;

    boolean deletePlayer(int playerId) throws Exception;

}
