package com.reponsitory;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Sale;
import com.entity.Trader;
import com.entity.TradingData;

/**
 * @author: HiepLe
 * @version: Mar 29, 2018
 */
public interface TradingDataRepository extends JpaRepository<TradingData, Integer> {

	@Query("select t.id, t.sale, t.dateRequest from TradingData t where t.trader.id = ?1 and t.status = ?2")
	public List<Object[]> getSaleRequestByTrader(int traderID, int status);
	
	@Query("select t.sale from TradingData t where t.trader.id = ?1 and t.status = ?2")
	public List<Sale> getSaleRequestForMaps(int traderID, int status);
	
	@Query("select t from TradingData t where t.sale.id = ?1 and t.status = ?2")
	public List<TradingData> getSaleRequest(int saleID, int status);
	
	@Query("select t.id, t.sale, t.dateSelected from TradingData t where t.trader.id = ?1 and t.status = ?2")
	public List<Object[]> getSaleSelectedByTrader(int traderID, int status);
	
	@Query("select t.sale from TradingData t where t.trader.id = ?1 and t.status = ?2")
	public List<Sale> getSaleSelectedForMaps(int traderID, int status);
	
	@Query("select t from TradingData t where t.sale.user.id = ?1 and t.status = ?2")
	public List<TradingData> getSaleSelectedByUser(int userID, int status);
	
	@Query("select t.sale.id from TradingData t where t.trader.id = ?1 and t.status >= ?2")
	public List<Integer> getSaleIDByTrader(int traderID, int status);
	
	@Query("select t.trader from TradingData t where t.id = ?1 and t.status >= ?2")
	public List<Trader> getTraderByDataID(int dataID, int status);
	
	@Query("select t.sale.id from TradingData t where t.id = ?1")
	public int getSaleID(int dataID);
	
	@Query("select max(t.dateSelected) from TradingData t where t.sale.user.id = ?1 and t.trader.id = ?2 and t.status = ?3")
	public Date getNewestSaleSelected(int userID, int traderID, int status);
	
	@Query("select t.trader from TradingData t where t.sale.id = ?1 and t.status = ?2")
	public Trader getTraderSelected(int saleID, int status);
	
	@Query("select t.trader from TradingData t where t.sale.id = ?1 and t.status = ?2")
	public List<Trader> getListTraderRequest(int saleID, int status);
	
	@Transactional
	@Modifying
	@Query("update TradingData t set t.status = ?2 where t.id=?1")
	public void updateState(int dataID, int status);
	
	@Transactional
	@Modifying
	@Query("update TradingData t set t.status = ?2 where t.sale.id=?1")
	public void updateStateBySaleID(int saleID, int status);
	
	@Query("select t.sale from TradingData t where t.trader.id = ?1 and t.status > 0 order by t.sale.agriculture.id desc")
	public List<Sale> getSaleForMining(int traderID);
	
	@Query("select t.sale.agriculture.name, count(t) from TradingData t where t.trader.id = ?1 and t.status > 0 group by t.sale.agriculture.name")
	public List<Object[]> getAgriForChar(int traderID);
	
	@Query("select t.sale.communeID, count(t) from TradingData t where t.trader.id = ?1 and t.status > 0 group by t.sale.communeID")
	public List<Object[]> getCommuneForChar(int traderID);
}
