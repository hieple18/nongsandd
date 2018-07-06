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
	
	@Query("select count(p) from AgriPrice p where p.date=?1")
	public int checkExistsPriceToday(Date date);

	@Query("select a from AgriPrice a where a.date=?1 and a.agriculture.status = 1")
	public List<AgriPrice> getPriceByday(Date date);
	
	@Query("select a.agriculture.id, a.price from AgriPrice a where a.date=?1")
	public List<Object[]> getForHashMap(Date date);
	
	@Query("select p.price from AgriPrice p where p.agriculture.id=?1")
	public List<Float> getPriceChart(int id);
	
	@Query("select p.price from AgriPrice p where p.agriculture.id=?1 and p.date = ?2")
	public float getPriceToCaculChange(int id, Date date);
	
	@Query("select a from AgriPrice a where a.date = ?1 and a.agriculture.status = 1")
	public List<AgriPrice> getPriceToUpdate(Date date);
	
	@Query("select min(a.price) from AgriPrice a where extract(month from a.date) = ?1 and a.agriculture.id = ?2 and a.agriculture.status = 1")
	public float getmin(int month, int id);
	
	@Query("select max(a.price) from AgriPrice a where extract(month from a.date) = ?1 and a.agriculture.id = ?2 and a.agriculture.status = 1")
	public float getmax(int month, int id);
	
	@Query("select avg(a.price) from AgriPrice a where extract(month from a.date) = ?1 and a.agriculture.id = ?2 and a.agriculture.status = 1")
	public float getavg(int month, int id);
}
