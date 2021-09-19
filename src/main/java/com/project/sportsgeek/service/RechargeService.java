package com.project.sportsgeek.service;

import com.project.sportsgeek.exception.ResultException;
import com.project.sportsgeek.model.Recharge;
import com.project.sportsgeek.model.profile.User;
import com.project.sportsgeek.repository.rechargerepo.RechargeRepository;
import com.project.sportsgeek.repository.userrepo.UserRepository;
import com.project.sportsgeek.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RechargeService {

    @Autowired
    @Qualifier("rechargeRepo")
    RechargeRepository rechargeRepository;

    @Autowired
    @Qualifier("userRepo")
    UserRepository userRepository;

    public Result<List<Recharge>> findAllRecharge() {
        List<Recharge> rechargeList = rechargeRepository.findAllRecharge();
        return new Result<>(200,rechargeList);
    }

    public Result<Recharge> findRechargeByRechargeId(int rechargeId) throws Exception {
        Recharge recharge = rechargeRepository.findRechargeByRechargeId(rechargeId);
        if (recharge != null) {
            return new Result<>(200, recharge);
        }
        throw new ResultException(new Result<>(404, "Recharge with RechargeId: " + rechargeId + " not found."));
    }

    public Result<List<Recharge>> findRechargeByUserId(int userId) throws Exception {
        List<Recharge> rechargeList = rechargeRepository.findRechargeByUserId(userId);
        if (rechargeList.size() > 0) {
            return new Result<>(200, rechargeList);
        }
        throw new ResultException(new Result<>(404, "Recharge for user with userId: "+ userId +" not found"));
    }

    public Result<Recharge> addRecharge(Recharge recharge) throws Exception {
        int rechargeId = rechargeRepository.addRecharge(recharge);
        if (rechargeId > 0) {
            recharge.setRechargeId(rechargeId);
            boolean result = userRepository.addAvailablePoints(recharge.getUserId(), recharge.getPoints());
            if(result){
                return new Result<>(201, recharge);
            }
            throw new ResultException(new Result<>(500, "Unable to update user available points."));
        }
        throw new ResultException(new Result<>(404, "Unable to add given recharge."));
    }

    public Result<Recharge> updateRecharge(int rechargeId, Recharge recharge) throws Exception {
        Recharge oldRecharge = rechargeRepository.findRechargeByRechargeId(rechargeId);
        if (rechargeRepository.updateRecharge(rechargeId, recharge)) {
            boolean result = userRepository.addAvailablePoints(oldRecharge.getUserId(), (recharge.getPoints() - oldRecharge.getPoints()));
            if(result){
                return new Result<>(200, recharge);
            }
            throw new ResultException(new Result<>(500, "Unable to update user available points."));
        }
        throw new ResultException(new Result<>(404, "Recharge with RechargeId: " + rechargeId + " not found."));
    }

    public Result<String> deleteRecharge(int rechargeId) throws Exception{
        // Get Recharge details before deleting
        Recharge recharge = rechargeRepository.findRechargeByRechargeId(rechargeId);
        if (rechargeRepository.deleteRecharge(rechargeId)) {
            boolean result = userRepository.deductAvailablePoints(recharge.getUserId(), recharge.getPoints());
            if(result){
                return new Result<>(200, "Recharge deleted successfully.");
            }
            throw new ResultException(new Result<>(500, "Unable to update user available points."));
        }
        throw new ResultException(new Result<>(404, "Recharge with RechargeId: " + rechargeId + " not found."));
    }
}