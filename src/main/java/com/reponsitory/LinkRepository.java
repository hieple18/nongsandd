package com.reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.ImgLink;

/**
 * @author: HiepLe
 * @version: Mar 20, 2018
 */
public interface LinkRepository extends JpaRepository<ImgLink, Integer>{
	
	@Query("select l.link from ImgLink l where l.sale.id = ?1")
	public List<String> getLinkSale(int saleID);
	
	@Query("select l from ImgLink l where l.sale.id = ?1")
	public List<ImgLink> getImgLink(int saleID);
	
	@Transactional
	@Modifying
	@Query("delete from ImgLink l where l.name = ?1 and l.sale.id = ?2")
	public void delLink(String name, int saleID);
}
