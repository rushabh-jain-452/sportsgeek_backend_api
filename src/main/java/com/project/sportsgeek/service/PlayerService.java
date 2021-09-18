package com.project.sportsgeek.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.project.sportsgeek.config.Config;
import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Player;
import com.project.sportsgeek.model.PlayerResponse;
import com.project.sportsgeek.model.Venue;
import com.project.sportsgeek.repository.playerrepo.PlayerRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    @Autowired
    @Qualifier(value = "playerRepo")
    PlayerRepository playerRepository;
    @Autowired
    ImageUploadService imageUploadService;

    public Result<List<PlayerResponse>> findAllPlayer() {
        List<PlayerResponse> playerList = playerRepository.findAllPlayers();
        return new Result<>(200, playerList);
    }

    public Result<PlayerResponse> findPlayerById(int playerId) throws Exception {
        PlayerResponse player = playerRepository.findPlayerByPlayerId(playerId);
        if (player != null) {
            return new Result<>(200, player);
        }
        throw new ResultException(new Result<>(404, "Player with PlayerId: " + playerId + " not found."));
    }

    public Result<List<PlayerResponse>> findPlayerByPlayerType(int playerTypeId) throws Exception {
        List<PlayerResponse> playerList = playerRepository.findPlayerByPlayerType(playerTypeId);
        return new Result<>(200, playerList);
    }

    public Result<List<PlayerResponse>> findPlayerByTeamId(int teamId) throws Exception {
        List<PlayerResponse> playerList = playerRepository.findPlayerByTeamId(teamId);
        return new Result<>(200, playerList);
    }

    public Result<Player> addPlayer(Player player, MultipartFile multipartFile) throws Exception {
        // Profile Picture
        if(multipartFile != null){
            File file = imageUploadService.uploadImage(multipartFile);
            if (file != null) {
                player.setProfilePicture(file.getName());
            }
            else{
                throw new ResultException(new Result<>(400, "Unable to upload Player Image."));
            }
        }
        else{
            player.setProfilePicture("");
        }
        // Add Player
        int playerId = playerRepository.addPlayer(player);
        if (playerId > 0) {
            player.setPlayerId(playerId);
            player.setProfilePicture(Config.FIREBASE_URL + player.getProfilePicture() + Config.FIREBASE_PARAMS);
            return new Result<>(201,"Player Added Successfully",player);
        }
        throw new ResultException(new Result<>(400, "Unable to add Player."));
    }

    public Result<Player> updatePlayer(int playerId, Player player, MultipartFile multipartFile) throws Exception {
        boolean result = false;
        // Profile Picture
        if(multipartFile != null){
            File file = imageUploadService.uploadImage(multipartFile);
            if (file != null) {
                player.setProfilePicture(file.getName());
                result = playerRepository.updatePlayerWithProfilePicture(playerId, player);
            }
            else{
                throw new ResultException(new Result<>(400, "Unable to upload the image."));
            }
        }
        else{
            result = playerRepository.updatePlayer(playerId, player);
        }
        // Update Player
        if (result == true) {
            player.setProfilePicture(Config.FIREBASE_URL + player.getProfilePicture() + Config.FIREBASE_PARAMS);
            return new Result<>(200, "Player Details Updated Successfully", player);
        }
        throw new ResultException(new Result<>(400, "Player with PlayerId: " + playerId + " not found."));
    }

    public Result<String> updatePlayerType(int playerId, int PlayerTypeId) throws Exception {
        if (playerRepository.updatePlayerType(playerId, PlayerTypeId)) {
            return new Result<>(200, "PlayerType Updated Successfully");
        }
        throw new ResultException(new Result<>(400, "Player with PlayerId: " + playerId + " not found."));
    }

    public Result<String> deletePlayer(int playerId) throws Exception{
        if (playerRepository.deletePlayer(playerId)) {
            return new Result<>(200, "Player Deleted Successfully");
        }
        throw new ResultException(new Result<>(400, "Player with PlayerId: " + playerId + " not found."));
    }
}
