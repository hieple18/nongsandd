package com.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Agriculture;
import com.models.PriceList;
import com.models.PriceR;
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
        return agricultureReponsitory.findAll();
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
    
    public List<Object[]> getPriceToday(){
    	Date today = Constant.CURRENT_DATE();
    	return agriPriceReponsitory.getPriceByday(today);
    }
    
    public List<Object[]> getPriceByday(String sdate){
    	Date date = Date.valueOf(sdate);
    	return agriPriceReponsitory.getPriceByday(date);
    }
    
    public List<Float> getPriceChart(int id){
    	return agriPriceReponsitory.getPriceChart(id);
    }
    
    public void AddAgri(int cID, String name){
    	Agriculture agriculture = new Agriculture(name, "/kg", new AgriCategory(cID));
    	agricultureReponsitory.save(agriculture);
    }
    
    public void randomPrice(){
    	agriPriceReponsitory.deleteAll();
        List<Agriculture> agricultures = agricultureReponsitory.findAll();
        Date today = Constant.CURRENT_DATE();
        Calendar cal = Calendar.getInstance();
        ArrayList<Integer> listi = new ArrayList<>();
        Random rand = new Random();
        
        for(int i=0;i<15;i++){
            cal.setTime(today);
            cal.add(Calendar.DATE, i*-1);
            Date date = new Date(cal.getTime().getTime());
            
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
    	agricultureReponsitory.delete(id);
    }

	public void createPrice(PriceList list){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date datePrevious = new Date(cal.getTime().getTime());
		List<PriceR> rs = list.getPrices();
		System.out.println(rs + ", " + rs.size());
		
		for (PriceR priceR : rs) {
			int agriID = priceR.getAgriID();
			float price = priceR.getPrice();
			System.out.println(price + ", " + agriID);
			AgriPrice agriPrice = new AgriPrice(price, 0, Constant.CURRENT_DATE(), new Agriculture(agriID));
			agriPriceReponsitory.save(agriPrice);
		}
	}
	
	public PriceList convertToPriceR(List<Agriculture> agricultures){
		List<PriceR> prices = new ArrayList<>();
		
		for (Agriculture agriculture : agricultures) {
			prices.add(new PriceR(0, agriculture.getId()));
		}
		
		return new PriceList(prices);
	}
	
	public PriceU getPriceUpdate(){
		Date today = Constant.CURRENT_DATE();
		List<AgriPrice> prices = agriPriceReponsitory.getPriceToUpdate(today);
		
		return new PriceU(prices);
	}
	
	public void saveUpdatePrice(PriceU priceU){
		List<AgriPrice> prices = priceU.getPrices();
		Date today = Constant.CURRENT_DATE();
		for (AgriPrice agriPrice : prices) {
			agriPrice.setDate(today);
			agriPrice.setPriceChange(0);
			
			agriPriceReponsitory.save(agriPrice);
		}
	}
}
