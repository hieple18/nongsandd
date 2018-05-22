package com.reponsitory;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.AgriPrice;

/**
 * @author HiepLe
 * @version 1.0 Dec 23, 2017
 */
public interface AgriPriceRepository extends JpaRepository<AgriPrice, Integer> {

	@Query("select min(p.date) from AgriPrice p")
	public Date getMinDatePrice();

	@Query("select a.id, a.name, a.agriCategory.id, p.price, p.priceChange from AgriPrice p, Agriculture a where "
			+"p.date=?1 and a.id = p.agriculture.id")
	public List<Object[]> getPriceByday(Date date);
	
	@Query("select p.price from AgriPrice p where p.agriculture.id=?1")
	public List<Float> getPriceChart(int id);
	
	@Query("select p.price from AgriPrice p where p.agriculture.id=?1 and p.date = ?2")
	public float getPriceToCaculChange(int id, Date date);
	
	@Query("select a from AgriPrice a where a.date = ?1")
	public List<AgriPrice> getPriceToUpdate(Date date);
}
