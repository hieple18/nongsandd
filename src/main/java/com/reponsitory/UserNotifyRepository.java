package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.UserNotification;

/**
 * @author: HiepLe
 * @version: Apr 11, 2018
 */
public interface UserNotifyRepository extends JpaRepository<UserNotification, Integer>{
	@Query("select un from UserNotification un where un.user.id = ?1 and un.status > 0")
	public List<UserNotification> getNotify(int userID);
	
	@Transactional
	@Modifying
	@Query("update UserNotification un set un.status = ?1 where un.id=?2")
	public void disableNotify(int state, int notifyID);
}
