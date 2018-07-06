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
    public Account getActiveAuth(String userName);
	
    @Query("select u from Account u where u.userName=?1 and u.enabled=1 and u.role=?2")
    public Account getActiveAccount(String userName, String role);
    
    @Query("select u from Account u where u.userName=?1 and u.role=?2")
    public Account getAccountByPhone(String phoneNum, String role);
}
