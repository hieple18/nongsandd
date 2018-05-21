package com.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.Account;

/**
*@author HiepLe
*@version 1.0 Dec 12, 2017
*/
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("select u from Account u where u.userName=?1 and u.enabled=1")
    public Account getActiveUser(String userName);
    
    @Query("select count(u) from Account u where u.userName=?1 and u.enabled=1")
    public int checkPhoneNum(String phoneNum);
}
