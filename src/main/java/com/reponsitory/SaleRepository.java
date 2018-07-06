package com.reponsitory;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Address;
import com.entity.Sale;
import com.entity.User;

/**
 * @author: HiepLe
 * @version: Mar 17, 2018
 */

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{
	
	@Query("select s from Sale s where s.user.id = ?1 and s.status > 0")
	public List<Sale> getListSaleByUser(int userID);
	
	@Query("select s from Sale s where s.agriculture.id in ?1 and s.id not in ?2 and s.status = 1 order by s.agriculture.id desc")
	public List<Sale> getListSaleByTrader(List<Integer> tradingAgri, List<Integer> tradingSeen);
	
	@Query("select s from Sale s where s.agriculture.id in ?1 and s.status = 1 order by s.agriculture.id desc")
	public List<Sale> getListSaleByTrader1(List<Integer> tradingAgri);
	
	@Query("select s.user from Sale s where s.id = ?1")
	public User getUser(int saleID);
	
	@Transactional
	@Modifying
	@Query("update Sale s set s.status = ?1 where s.id=?2")
	public void updateState(int state, int saleID);
	
	@Transactional
	@Modifying
	@Query("update Sale s set s.requestCount = s.requestCount + 1 where s.id=?1")
	public void RequestCountUp(int saleID);
	
	@Transactional
	@Modifying
	@Query("update Sale s set s.requestCount = s.requestCount - 1 where s.id=?1")
	public void RequestCountDown(int saleID);
	
	@Query("select s.id, s.agriculture.name, s.address.lat, s.address.lng from Sale s where s.status = 1")
	public List<Object[]> getInfoForMaps();
	
	@Query("select count(s) from Sale s where s.agriculture.id = ?1 and s.status >= ?2")
	public int countAgriSale(int id, int status);
	
	@Transactional
	@Modifying
	@Query("update Sale s set s.status = 0 where s.status = 1 and s.dateCreate < ?1")
	public void updateSaleExpired(Date dateExperid);
	
	@Query("select s, s.address.hamlet.hamletID from Sale s")
	public List<Object[]> getSaleCommune();
	
	@Query("select s.address from Sale s where s.id = ?1")
	public Address getAddress(int saleID);
	
	@Query("select s from Sale s")
	public List<Sale> getSaleForExcel();
	
	@Query("select s from Sale s where s.id = ?1")
	public Sale getSaleForUpdateMining(int saleID);
	
	@Query("select count(s) from Sale s where s.user.id = ?1 and extract(month from s.dateCreate) = ?2 and s.status = 1")
	public int countSalePerMonth(int userID, int month);
}
