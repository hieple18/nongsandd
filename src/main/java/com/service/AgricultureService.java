package com.service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Agriculture;
import com.models.PriceList;
import com.models.PriceR;
import com.models.PriceRecord;
import com.models.PriceU;
import com.constant.Constant;
import com.entity.AgriCategory;
import com.entity.AgriPrice;
import com.reponsitory.AgriCategoryRepository;
import com.reponsitory.AgriPriceRepository;
import com.reponsitory.AgricultureRepository;

/**
*@author HiepLe
*@version 1.0 Dec 19, 2017
*/

@Service
public class AgricultureService {
    
    @Autowired 
    private AgricultureRepository agricultureReponsitory;
    
/*    @Autowired
    private AgriSubCategoryReponsitory agriSubCategoryReponsitory;*/
    
    @Autowired 
    private AgriCategoryRepository agriCategoryReponsitory;
    
    @Autowired
    private AgriPriceRepository agriPriceReponsitory;
    
    DateFormat outputFormatter = new SimpleDateFormat("MM/dd/yyyy");
    
    public List<Agriculture> getAllAgri(){
        return agricultureReponsitory.getAllAvailable();
    }
    
    public List<Agriculture> getAgribySubID(int id){
        return agricultureReponsitory.getAgriBySub(id);
    }
    
    public List<AgriCategory> getAllAgriCategory(){
        return agriCategoryReponsitory.findAll();
    }
    
    public Date getMinDatePrice(){
    	return agriPriceReponsitory.getMinDatePrice();
    }
    
    public boolean checkExistsPriceToday(){
    	int count = agriPriceReponsitory.checkExistsPriceToday(Constant.CURRENT_DATE());
    	
    	return (count > 0)?true:false;
    }
    
    public List<PriceRecord> getPriceToday(){
    	Date today = Constant.CURRENT_DATE();
    	int month = Constant.CURRENT_MONTH();
    	
    	List<AgriPrice> prices = agriPriceReponsitory.getPriceByday(today);
    	List<PriceRecord> records = new ArrayList<>();
    	
    	for (AgriPrice price : prices) {
			int id = price.getAgriculture().getId();
			String name = price.getAgriculture().getName();
			float pri = price.getPrice();
			float change = price.getPriceChange();
			float min = agriPriceReponsitory.getmin(month, id);
			float max = agriPriceReponsitory.getmax(month, id);
			float avg = agriPriceReponsitory.getavg(month, id);
			
			PriceRecord priceRecord = new PriceRecord(id, name, pri, change, min, max, avg);
			records.add(priceRecord);
		}
    	
    	return records;
    }
    
    public List<PriceRecord> getPriceByday(String sdate){
    	Date date = Date.valueOf(sdate);
    	int month = Constant.CURRENT_MONTH();
    	
    	List<AgriPrice> prices = agriPriceReponsitory.getPriceByday(date);
    	List<PriceRecord> records = new ArrayList<>();
    	
    	for (AgriPrice price : prices) {
			int id = price.getAgriculture().getId();
			String name = price.getAgriculture().getName();
			float pri = price.getPrice();
			float change = price.getPriceChange();
			float min = agriPriceReponsitory.getmin(month, id);
			float max = agriPriceReponsitory.getmax(month, id);
			float avg = agriPriceReponsitory.getavg(month, id);
			
			PriceRecord priceRecord = new PriceRecord(id, name, pri, change, min, max, avg);
			records.add(priceRecord);
		}
    	
    	return records;
    }
    
    public List<Float> getPriceChart(int id){
    	return agriPriceReponsitory.getPriceChart(id);
    }
    
    public void AddAgri(int cID, String name){
    	Agriculture agriculture = new Agriculture(name, "", Constant.ENABLE_STATE, new AgriCategory(cID));
    	agricultureReponsitory.save(agriculture);
    }
    
    public void updateAgri(int id, int cID, String name){
    	Agriculture agriculture = new Agriculture(name, "", Constant.ENABLE_STATE, new AgriCategory(cID));
    	agriculture.setId(id);
    	agricultureReponsitory.save(agriculture);
    }
    
    public void randomPrice(){
    	agriPriceReponsitory.deleteAll();
        List<Agriculture> agricultures = agricultureReponsitory.getAllAvailable();
        ArrayList<Integer> listi = new ArrayList<>();
        Random rand = new Random();
        
        for(int i=0;i<15;i++){
            Date date = SubDateFromNow(i);
            
            for(int j = 0; j<agricultures.size();j++){
                if(i==0){
                    int ran = rand.nextInt(25) + 5;
                    agriPriceReponsitory.save(new AgriPrice(ran, 0, date, agricultures.get(j)));
                    listi.add(ran);
                }else{
                    int ran = rand.nextInt(5) - 2;
                    int newPrice;
                    if(listi.get(j) > 2)
                        newPrice = listi.get(j) + ran;
                    else
                        newPrice = listi.get(j) + Math.abs(ran);
                    listi.set(j, newPrice);
                    agriPriceReponsitory.save(new AgriPrice(newPrice, ran, date, agricultures.get(j)));
                }
            }
        }
    }
    
    public void deleteAgri(int id){
    	Agriculture agri = agricultureReponsitory.findOne(id);
    	agri.setStatus(Constant.DISABLE_STATE);
    	agricultureReponsitory.save(agri);
    }

    public Date SubDateFromNow(int count){
    	Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -count);
		return new Date(cal.getTime().getTime());
    }
    
    public HashMap<Integer, Float> getPreviousPrice(){
    	Date previousDate = SubDateFromNow(1);
    	List<Object[]> prices = agriPriceReponsitory.getForHashMap(previousDate);
    	HashMap<Integer, Float> maps = new HashMap<Integer, Float>();
    	
    	for (Object[] object : prices) {
			maps.put((int)object[0], (float)object[1]);
		}
    	
    	return maps;
    }
    
	public void createPrice(PriceList list){
		HashMap<Integer, Float> prevPrice = getPreviousPrice();
		List<PriceR> rs = list.getPrices();
		
		for (PriceR priceR : rs) {
			int id = priceR.getAgriID();
			float price = priceR.getPrice();
			float change = prevPrice.containsKey(id) ? price - prevPrice.get(id) : 0;
			AgriPrice agriPrice = new AgriPrice(price, change, Constant.CURRENT_DATE(), new Agriculture(id));
			agriPriceReponsitory.save(agriPrice);
		}
	}
	
	public PriceList convertToPriceR(List<Agriculture> agricultures){
		List<PriceR> prices = new ArrayList<>();
		
		for (Agriculture agriculture : agricultures) {
			prices.add(new PriceR(0, agriculture.getId(), agriculture.getName()));
		}
		
		return new PriceList(prices);
	}
	
	public PriceU getPriceUpdate(){
		Date today = Constant.CURRENT_DATE();
		List<AgriPrice> prices = agriPriceReponsitory.getPriceToUpdate(today);
		
		return new PriceU(prices);
	}
	
	public void updateChange(){
		HashMap<Integer, Float> prevPrice = getPreviousPrice();
		List<AgriPrice> prices = agriPriceReponsitory.getPriceByday(Constant.CURRENT_DATE());
		for (AgriPrice price : prices) {
			int id = price.getAgriculture().getId();
			float change = prevPrice.containsKey(id) ? price.getPrice() - prevPrice.get(id) : 0;
			price.setPriceChange(change);
			agriPriceReponsitory.save(price);
		}
	}
	
	public void saveUpdatePrice(PriceU priceU){
		List<AgriPrice> prices = priceU.getPrices();
		Date today = Constant.CURRENT_DATE();
		for (AgriPrice agriPrice : prices) {
			agriPrice.setDate(today);
			agriPrice.setPriceChange(0);
			
			agriPriceReponsitory.save(agriPrice);
		}
		
		updateChange();
	}
}
