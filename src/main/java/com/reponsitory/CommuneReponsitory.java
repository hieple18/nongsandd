package com.reponsitory;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.Commune;

/**
*@author HiepLe
*@version 1.0 Dec 9, 2017
*/
@org.springframework.transaction.annotation.Transactional
@Repository
public interface CommuneReponsitory extends JpaRepository<Commune, Integer>{
    
    @Query("select c from Commune c where c.district.districtID = ?1")
    public List<Commune> getCommuneByDistrictID(int districtID);
    
    @Query("select c.name from Commune c where c.communeID = ?1")
    public String getCommuneName(int communeID);
}
