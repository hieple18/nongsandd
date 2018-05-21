package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.Mining;

/**
 * @author: HiepLe
 * @version: May 16, 2018
 */
public interface MiningRepository extends JpaRepository<Mining, Integer>{
	@Query("select m from Mining m where m.trader.id = ?1 and m.agriID = ?2")
	public Mining getMiningByTrader(int traderID, int agriID);
	
	@Query("select m from Mining m where m.trader.id = ?1")
	public List<Mining> getAllMiningByTrader(int traderID);
}
