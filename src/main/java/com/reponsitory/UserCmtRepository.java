package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.UserCmt;

/**
 * @author: HiepLe
 * @version: Apr 17, 2018
 */
public interface UserCmtRepository extends JpaRepository<UserCmt, Integer>{
	@Query("select c from UserCmt c where c.user.id = ?1")
	public List<UserCmt> getCmt(int userID);
	
	@Query("select c.id from UserCmt c where c.user.id = ?1 and c.trader.id = ?2")
	public List<Integer> getIDCmtToShowEdit(int userID, int TraderID);
	
	@Query("select count(c) from UserCmt c where c.user.id = ?1")
	public int getCountCmt(int userID);
	
	@Query("select c.trader.id from UserCmt c where c.id = ?1")
    public int getTraderID(int id);
	
	@Query("select c.ratingCount from UserCmt c where c.id = ?1")
    public int getRatingStar(int id);
}
