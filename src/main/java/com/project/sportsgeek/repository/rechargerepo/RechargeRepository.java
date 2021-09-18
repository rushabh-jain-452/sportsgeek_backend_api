package com.project.sportsgeek.repository.rechargerepo;

import com.project.sportsgeek.model.Recharge;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "rechargeRepo")
public interface RechargeRepository {

    List<Recharge> findAllRecharge();

    Recharge findRechargeByRechargeId(int rechargeId) throws Exception;

    List<Recharge> findRechargeByUserId(int userId) throws Exception;

    int addRecharge(Recharge Recharge) throws Exception;

    boolean updateRecharge(int rechargeId, Recharge Recharge) throws Exception;

    boolean deleteRecharge(int rechargeId) throws Exception;

    boolean deleteRechargeByUserId(int userId) throws Exception;

}
