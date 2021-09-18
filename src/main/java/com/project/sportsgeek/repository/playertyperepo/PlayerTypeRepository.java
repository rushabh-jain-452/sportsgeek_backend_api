package com.project.sportsgeek.repository.playertyperepo;

import com.project.sportsgeek.model.PlayerType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "playerTypeRepo")
public interface PlayerTypeRepository {

    List<PlayerType> findAllPlayerType();

    PlayerType findPlayerTypeById(int playerTypeId) throws Exception;

    int addPlayerType(PlayerType PlayerType) throws Exception;

    boolean updatePlayerType(int playerTypeId, PlayerType PlayerType) throws Exception;

    boolean deletePlayerType(int playerTypeId) throws Exception;

}
