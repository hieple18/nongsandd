package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Address;
import com.entity.Trader;

/**
 * @author: HiepLe
 * @version: Mar 16, 2018
 */
@Repository
public interface TraderRepository extends JpaRepository<Trader, Integer>{
	@Query("select id from Trader t where t.phoneNum = ?1")
    public int getTraderIDByPhone(String phoneNum);
	
	@Query("select t from Trader t where t.phoneNum = ?1")
    public Trader getTraderByPhone(String phoneNum);
	
	@Query("select t from Trader t where t.phoneNum = ?1 and status = ?2")
    public Trader getTraderNotActive(String phoneNum, int status);
	
	@Query("select t.name from Trader t where t.phoneNum = ?1")
    public String getNameByPhone(String phoneNum);
	
	@Query("select t.ratingSum from Trader t where t.id = ?1")
    public float getRatingSum(int id);
	
	@Transactional
	@Modifying
	@Query("update Trader t set t.ratingSum = ?1 where t.id=?2")
    public void updateRatingSum(float sum, int id);
	
	@Query("select count(t) from Trader t")
	public int getCountTrader();
	
	@Query("select t.id, t.name, t.address.lat, t.address.lng from Trader t")
	public List<Object[]> getInfoForMaps();
	
	@Query("select t.address from Trader t where t.id = ?1")
	public Address getAddress(int traderID);
	
	@Query("select count(t) from Trader t where t.phoneNum = ?1 and t.status = ?2")
	public int getTraderWaitToVerify(String phoneNum, int status);
	
	@Query("select t from Trader t where t.status = ?1")
	public List<Trader> getTraderWaitToRegister(int status);
	
	@Query("select t from Trader t where t.phoneNum = ?1 and t.status = ?2")
	public Trader getVerifiedTrader(String phoneNum, int status);
	
	@Transactional
	@Modifying
	@Query("update Trader t set t.status = ?1 where t.id=?2")
    public void updateStatus(int state, int id);
}
