package com.project.sportsgeek.repository.emailcontactrepo;

import com.project.sportsgeek.model.EmailContact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "emailContactRepo")
public interface EmailContactRepository {

    List<EmailContact> findAllEmailContact();

    EmailContact findEmailContactById(int emailContactId) throws Exception;

    int addEmailContact(EmailContact emailContact) throws Exception;

    boolean updateEmailContact(int emailContactId, EmailContact emailContact) throws Exception;

    boolean updateEmailContactByUserId(int userId, EmailContact emailContact) throws Exception;

    boolean deleteEmailContact(int emailContactId) throws Exception;

    boolean deleteEmailContactByUserId(int userId) throws Exception;

    int getCountByEmailId(String emailId) throws Exception;
}
