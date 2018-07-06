package com.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.Mining;

/**
 * @author: HiepLe
 * @version: May 16, 2018
 */
public interface MiningRepository extends JpaRepository<Mining, Integer>{
	@Query("select m from Mining m where m.trader.id = ?1")
	public Mining getMiningByTrader(int traderID);
}
