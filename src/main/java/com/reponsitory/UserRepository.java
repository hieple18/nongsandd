package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Address;
import com.entity.User;

/**
 * @author: HiepLe
 * @version: Mar 15, 2018
 */

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select id from User u where u.phoneNum = ?1")
    public int getUserIDByPhone(String phoneNum);
	
	@Query("select u from User u where u.phoneNum = ?1")
    public User getUserByPhone(String phoneNum);
	
	@Query("select u.name from User u where u.phoneNum = ?1")
    public String getNameByPhone(String phoneNum);
	
	@Query("select u from User u where u.id = ?1")
    public User getUser(int id);
	
	@Query("select u.ratingSum from User u where u.id = ?1")
    public float getRatingSum(int id);
	
	@Transactional
	@Modifying
	@Query("update User u set u.ratingSum = ?1 where u.id=?2")
    public void updateRatingSum(float sum, int id);
	
	@Query("select count(u) from User u")
	public int getCountUser();
	
	@Query("select u.id, u.name, u.address.lat, u.address.lng from User u")
	public List<Object[]> getInfoForMaps();
	
	@Query("select max(u.id) from User u")
	public int getMaxID();
	
	@Query("select u.id from User u")
	public List<Integer> getListID();
	
	@Query("select u.address from User u where u.id = ?1")
	public Address getAddress(int userID);
}
