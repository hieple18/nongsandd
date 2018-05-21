package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Hamlet;

/**
*@author HiepLe
*@version 1.0 Dec 9, 2017
*/
@Repository
public interface HamletRepository extends JpaRepository<Hamlet, Integer> {
    
    @Query("select h from Hamlet h where h.commune.communeID = ?1")
    public List<Hamlet> getHamletByCommuneID(int communeID);
    
    @Query("select h.commune.communeID from Hamlet h where h.hamletID = ?1)")
    public Integer getCommuneIDByHamletID(int hamletID);
    
    @Query("select h.id, h.name from Hamlet h where h.commune.communeID = ?1")
    public List<Object[]> getHamletNameByCommuneID(int communeID);
    
    @Query("select h.name from Hamlet h where h.hamletID = ?1")
    public String getHamletName(int hamletID);

    @Transactional
	@Modifying
	@Query("update Hamlet h set h.address = ?1 where h.hamletID = ?2")
	public void updateAddress(String address, int hamletID);
    
}
