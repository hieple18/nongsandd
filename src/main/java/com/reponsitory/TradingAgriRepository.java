package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.TradingAgri;

/**
 * @author: HiepLe
 * @version: Mar 29, 2018
 */
public interface TradingAgriRepository extends JpaRepository<TradingAgri, Integer>{

	@Query("select t.agriculture.id from TradingAgri t where t.trader.id = ?1 and t.agriculture.status = 1")
	public List<Integer> getTradingAgriByTrader(int traderID);
	
	@Query("select t.agriculture.name from TradingAgri t where t.trader.id = ?1 and t.agriculture.status = 1")
	public List<String> getTradingAgriName(int traderID);
	
	@Transactional
	@Modifying
	@Query("delete from TradingAgri t where t.trader.id = ?1")
	public void deleteByTraderID(int traderID);
}
