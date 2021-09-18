package com.project.sportsgeek.service;


import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.PlayerType;
import com.project.sportsgeek.repository.playertyperepo.PlayerTypeRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerTypeService {

    @Autowired
    @Qualifier("playerTypeRepo")
    PlayerTypeRepository playerTypeRepository;

    public Result<List<PlayerType>> findAllPlayerType() {
        List<PlayerType> playerTypeList = playerTypeRepository.findAllPlayerType();
        return new Result<>(200,playerTypeList);
    }

    public Result<PlayerType> findPlayerTypeById(int playerTypeId) throws Exception {
        PlayerType playerType = playerTypeRepository.findPlayerTypeById(playerTypeId);
        if (playerType != null) {
            return new Result<>(200, playerType);
        }
        throw new ResultException(new Result<>(404, "PlayerType with PlayerTypeId: " + playerTypeId + " not found."));
    }

    public Result<PlayerType> addPlayerType(PlayerType playerType) throws Exception {
        int playerTypeId = playerTypeRepository.addPlayerType(playerType);
        if (playerTypeId > 0) {
            playerType.setPlayerTypeId(playerTypeId);
            return new Result<>(201,playerType);
        }
        throw new ResultException(new Result<>(400, "Unable to add Player Type."));
    }

    public Result<PlayerType> updatePlayerType(int playerTypeId, PlayerType playerType) throws Exception {
        if (playerTypeRepository.updatePlayerType(playerTypeId,playerType)) {
            return new Result<>(200,playerType);
        }
        throw new ResultException(new Result<>(404, "Player Type with PlayerTypeId: " + playerTypeId + " not found."));
    }

    public Result<String> deletePlayerType(int playerTypeId) throws Exception{
        if (playerTypeRepository.deletePlayerType(playerTypeId)) {
            return new Result<>(200, "Player Type deleted successfully.");
        }
        throw new ResultException(new Result<>(404, "Player Type with PlayerTypeId: " + playerTypeId + " not found."));
    }
}
