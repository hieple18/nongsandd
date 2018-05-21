package com.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Agriculture;
import com.models.PriceList;
import com.models.PriceR;
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
    	Date date = new Date();
		try {
			date = outputFormatter.parse(sdate);
			System.out.println("date");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
        cal.setTime(today);
        ArrayList<Integer> listi = new ArrayList<>();
        Random rand = new Random();
        
        for(int i=0;i<15;i++){
            cal.setTime(today);
            cal.add(Calendar.DATE, i*-1);
            Date date = cal.getTime();
            
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
    
    public void createPriceFile() throws IOException{
    	List<Float> listPrice = null;
    	
    	List<Agriculture> agricultures = agricultureReponsitory.findAll();
    	for (Agriculture agriculture : agricultures) {
    		int id = agriculture.getId();
			String fileName = "data/price/" + agriculture.getId() + ".txt";
			System.out.println(fileName);
			
			listPrice = agriPriceReponsitory.getPriceChart(id);
			
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			FileWriter fr = null;
			BufferedWriter out = null;
	        try {
	            fr = new FileWriter(file);
	            String temp = listPrice.toString();
	            out = new BufferedWriter(fr);
	            System.out.println(temp);
	            out.write(temp);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            //close resources
	            try {
	            	out.flush();
	            	out.close();
	            	fr.flush();
	                fr.close();
	            } catch (IOException e) {
	                e.printStackTrace();
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
		List<PriceR> rs = list.getLists();
		System.out.println(rs + ", " + rs.size());
		
		for (PriceR priceR : rs) {
			int agriID = priceR.getAgriID();
			float price = priceR.getPrice();
			float pricePrevious = agriPriceReponsitory.getPriceToCaculChange(agriID, datePrevious);
			System.out.println(agriID + ", " + price + ", " + pricePrevious);
			AgriPrice agriPrice = new AgriPrice(price, price - pricePrevious, Constant.CURRENT_DATE(), new Agriculture(agriID));
			agriPriceReponsitory.save(agriPrice);
		}
	}
	
	public PriceList convertToPriceR(List<Agriculture> agricultures){
		List<PriceR> prices = new ArrayList<>();
		
		for (Agriculture agriculture : agricultures) {
			prices.add(new PriceR(agriculture.getId(), 0));
		}
		
		return new PriceList(prices);
	}
}
