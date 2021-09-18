package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.PublicChat;
import com.project.sportsgeek.model.PublicChatFormatted;
import com.project.sportsgeek.model.PublicChatWithUser;
import com.project.sportsgeek.repository.publicchatrepo.PublicChatRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PublicChatService {

    @Autowired
    @Qualifier(value = "publicChatRepo")
    PublicChatRepository publicChatRepository;

//    public Result<List<PublicChatWithUser>> findAllPublicChat() {
//        List<PublicChatWithUser> publicChatList = publicChatRepository.findAllPublicChat();
//        return new Result<>(200, publicChatList);
//    }

    public Result<List<PublicChatWithUser>> findAllPublicChatForLastDays(int days) {
        List<PublicChatWithUser> publicChatList = publicChatRepository.findAllPublicChatForLastDays(days);
        return new Result<>(200, publicChatList);
    }

    public Result<List<PublicChatWithUser>> findAllPublicChatAfterId(int publicChatId) {
        List<PublicChatWithUser> publicChatList = publicChatRepository.findAllPublicChatAfterId(publicChatId);
        return new Result<>(200, publicChatList);
    }

//    public Result<List<PublicChatFormatted>> findAllPublicChatFormatted() {
//        List<PublicChatFormatted> publicChatList = publicChatRepository.findAllPublicChatFormatted();
//        return new Result<>(200, publicChatList);
//    }

    public Result<List<PublicChatFormatted>> findAllPublicChatFormattedForLastDays(int days) {
        List<PublicChatFormatted> publicChatList = publicChatRepository.findAllPublicChatFormattedForLastDays(days);
        return new Result<>(200, publicChatList);
    }

    public Result<List<PublicChatFormatted>> findAllPublicChatFormattedAfterId(int publicChatId) {
        List<PublicChatFormatted> publicChatList = publicChatRepository.findAllPublicChatFormattedAfterId(publicChatId);
        return new Result<>(200, publicChatList);
    }

    public Result<PublicChatWithUser> findPublicChatById(int publicChatId) throws Exception {
        PublicChatWithUser publicChat = publicChatRepository.findPublicChatById(publicChatId);
        if (publicChat != null) {
            return new Result<>(200, publicChat);
        }
        throw new ResultException(new Result<>(404, "Public Chat with PublicChatId: " + publicChatId + " not found."));
    }

    public Result<PublicChat> addPublicChat(PublicChat publicChat) throws Exception {
        int id = publicChatRepository.addPublicChat(publicChat);
        if (id > 0) {
            publicChat.setPublicChatId(id);
            publicChat.setStatus(true);
            return new Result<>(201, publicChat);
        }
        throw new ResultException(new Result<>(404, "Unable to add Public Chat."));
    }

    public Result<PublicChat> updatePublicChat(int publicChatId, PublicChat publicChat) throws Exception {
        if (publicChatRepository.updatePublicChat(publicChatId,publicChat)) {
            return new Result<>(200, publicChat);
        }
        throw new ResultException(new Result<>(404, "Public Chat with PublicChatId: " + publicChatId + " not found."));
    }

    public Result<String> updatePublicChatStatus(int publicChatId, boolean status) throws Exception {
        if (publicChatRepository.updatePublicChatStatus(publicChatId, status)) {
            return new Result<>(200, "Status of PublicChatId: " + publicChatId + " updated successfully.");
        }
        throw new ResultException(new Result<>(404, "Public Chat with PublicChatId: " + publicChatId + " not found."));
    }

    public Result<String> deletePublicChat(int publicChatId) throws Exception{
        if (publicChatRepository.deletePublicChat(publicChatId)) {
            return new Result<>(200, "Public Chat Deleted Successfully");
        }
        throw new ResultException(new Result<>(404, "Public Chat with PublicChatId: " + publicChatId + " not found."));
    }
}