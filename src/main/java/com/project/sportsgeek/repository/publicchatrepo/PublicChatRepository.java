package com.project.sportsgeek.repository.publicchatrepo;

import com.project.sportsgeek.model.PublicChat;
import com.project.sportsgeek.model.PublicChatFormatted;
import com.project.sportsgeek.model.PublicChatWithUser;
import com.project.sportsgeek.model.Venue;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "publicChatRepo")
public interface PublicChatRepository {

//    List<PublicChatWithUser> findAllPublicChat();
    List<PublicChatWithUser> findAllPublicChatForLastDays(int days);
    List<PublicChatWithUser> findAllPublicChatAfterId(int publicChatId);

//    List<PublicChatFormatted> findAllPublicChatFormatted();
    List<PublicChatFormatted> findAllPublicChatFormattedForLastDays(int days);
    List<PublicChatFormatted> findAllPublicChatFormattedAfterId(int publicChatId);

    List<PublicChatWithUser> findAllTodayPublicChat();

    PublicChatWithUser findPublicChatById(int publicChatId) throws Exception;

    int addPublicChat(PublicChat publicChat) throws Exception;

    boolean updatePublicChat(int publicChatId, PublicChat publicChat) throws Exception;
    boolean updatePublicChatStatus(int publicChatId, boolean status) throws Exception;

    boolean deletePublicChat(int publicChatId) throws Exception;
    boolean deletePublicChatByUserId(int userId) throws Exception;

}
