package com.reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Address;

/**
 * @author: HiepLe
 * @version: Mar 15, 2018
 */
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	@Transactional
	@Modifying
	@Query("update Address a set a.address = a.hamlet.name")
	public void updateAddress();
	
	@Query("select a.hamlet.hamletID from Address a where a.id = ?1")
	public int getHamletIDByAddress(int addressID);
}
