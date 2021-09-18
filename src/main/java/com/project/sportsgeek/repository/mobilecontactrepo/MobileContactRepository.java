package com.project.sportsgeek.repository.mobilecontactrepo;

import com.project.sportsgeek.model.MobileContact;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "mobileContactRepo")
public interface MobileContactRepository {
    
    List<MobileContact> findAllMobileContact();

    MobileContact findMobileContactById(int mobileContactId) throws Exception;

    int addMobileContact(MobileContact mobileContact) throws Exception;

    boolean updateMobileContact(int mobileContactId, MobileContact mobileContact) throws Exception;

    boolean updateMobileContactByUserId(int userId, MobileContact mobileContact) throws Exception;

    boolean deleteMobileContact(int mobileContactId) throws Exception;

    boolean deleteMobileContactByUserId(int userId) throws Exception;
}
