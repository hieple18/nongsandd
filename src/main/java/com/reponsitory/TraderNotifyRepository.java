package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.TraderNotification;

/**
 * @author: HiepLe
 * @version: Apr 11, 2018
 */
public interface TraderNotifyRepository extends JpaRepository<TraderNotification, Integer>{
	@Query("select un from TraderNotification un where un.trader.id = ?1 and un.status > 0")
	public List<TraderNotification> getNotify(int traderID);
	
	@Transactional
	@Modifying
	@Query("update TraderNotification tn set tn.status = ?1 where tn.id=?2")
	public void disableNotify(int state, int notifyID);
}
