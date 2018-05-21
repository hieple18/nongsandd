package com.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.Constant;
import com.entity.Address;
import com.entity.Hamlet;
import com.entity.ImgLink;
import com.entity.Mining;
import com.entity.Sale;
import com.entity.Trader;
import com.entity.TradingData;
import com.entity.User;
import com.models.PieChart;
import com.models.SaleScore;
import com.reponsitory.LinkRepository;
import com.reponsitory.MiningRepository;
import com.reponsitory.SaleRepository;
import com.reponsitory.TradingAgriRepository;
import com.reponsitory.TradingDataRepository;

/**
 * @author: HiepLe
 * @version: Mar 17, 2018
 */
@Service
public class SaleService {
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	private LinkRepository linkRepository;
	
	@Autowired
    private AddressService addressService;
	
	@Autowired
	private TraderService traderService;
	
	@Autowired
    private TradingDataRepository dataRepository;
	
	@Autowired
    private TradingAgriRepository tradingAgriRepository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private MiningRepository miningRepository;
	
	public Address addAddress(Sale sale, int hamletID){
		double lng = sale.getAddress().getLng();
		double lat = sale.getAddress().getLat();
		
		Address address = new Address(lat, lng, new Hamlet());
		addressService.createAddress(address, hamletID);
		
		return address;
	}
	
	public void addLinks(Sale sale, List<String> links){
		ImgLink imgLink;
		for (String link : links) {
			String[] parts = link.split(",");
			try{
				imgLink = new ImgLink(parts[0], parts[1], sale);
				linkRepository.save(imgLink);
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println(e + link);
			}
		}
	}
	
	public void delLinks(int saleID, List<String> links){
		for (String link : links) {
			linkRepository.delLink(link, saleID);
		}
	}
	
	public void createSale(Sale sale, List<String> links, int hamletID, User user){
		sale.setUser(user);
		
		Address address = addAddress(sale, hamletID);
		sale.setAddress(address);
		int communeID = addressService.getCommuneIDByHamletID(hamletID);
		sale.setCommuneID(communeID);
		
		sale.setDateCreate(Constant.CURRENT_DATE());
		sale.setStatus(Constant.ENABLE_STATE);
		saleRepository.save(sale);
		
		addLinks(sale, links);
	}
	
	public void deleteSale(int saleID, User user){
		// update sale status
		Sale sale = saleRepository.findOne(saleID);
		if(sale.getUser().getId() == user.getId() && sale.getStatus() != 2){ // not in case someone mess up db
			sale.setStatus(Constant.DELETE_STATE);
			saleRepository.save(sale);
		}
	}
	
	public void deleteSaleSelected(int dataID, int traderID){
		TradingData data = dataRepository.findOne(dataID);
		dataRepository.delete(dataID);
		
		Trader traderToConfirm = data.getTrader();
		if(traderToConfirm.getId() == traderID){
			int saleID = data.getSale().getId();
			saleRepository.updateState(Constant.DISABLE_STATE, saleID);
			User user = saleRepository.getUser(saleID);
			
			userService.createNotification(saleID, Constant.U_SALE_REMOVE, user, traderToConfirm);
		}
		
	}
	
	public void createNotifySaleUpdate(Sale sale, User user){
		if(sale.getStatus() == Constant.SELECTED_STATE){
			Trader trader = dataRepository.getTraderSelected(sale.getId(), Constant.SELECTED_STATE);
			traderService.createNotification(sale.getId(), Constant.T_UPDATE_SELECTED, user, trader);
		}else{
			List<Trader> traders = dataRepository.getListTraderRequest(sale.getId(), Constant.ENABLE_STATE);
			for (Trader trader : traders) {
				traderService.createNotification(sale.getId(), Constant.T_UPDATE_REQUEST, user, trader);
			}
		}
	}
	
	public void updateSale(User user, Sale sale, List<String> addLinks, List<String> delLinks, int hamletID){
		User confirmUser = saleRepository.getUser(sale.getId());
		
		if(user.getId() == confirmUser.getId()){ // not in case someone mess up db
			Address address = sale.getAddress();
			addressService.createAddress(address, hamletID);
			
			sale.setUser(user);
			saleRepository.save(sale);
			
			if(delLinks != null){
				delLinks(sale.getId(), delLinks);}
			if(addLinks != null){
				addLinks(sale, addLinks);}
			
			createNotifySaleUpdate(sale, user);
		}
	}
	
	public List<Sale> getListSaleByUser(int userID){
		return saleRepository.getListSaleByUser(userID);
	}
	
	
	public List<String> getLinkSale(int saleID){
		return linkRepository.getLinkSale(saleID);
	}
	
	public List<ImgLink> getImgLink(int saleID){
		return linkRepository.getImgLink(saleID);
	}
	
	public Sale getSale(int saleID){
		return saleRepository.findOne(saleID);
	}
	
	public List<Sale> getSaleListByTrader(int traderID){
		List<Integer> tradingAgri = tradingAgriRepository.getTradingAgriByTrader(traderID);
		List<Integer> tradingSeen = dataRepository.getSaleIDByTrader(traderID, Constant.ENABLE_STATE);
		if(tradingSeen.size() > 0){
			return saleRepository.getListSaleByTrader(tradingAgri, tradingSeen);
		}else{
			return saleRepository.getListSaleByTrader1(tradingAgri);
		}
	}
	
	public List<Integer> getTradingAgriID(int traderID){
		return tradingAgriRepository.getTradingAgriByTrader(traderID);
	}
	
	public List<String> getTradingAgriName(int traderID){
		return tradingAgriRepository.getTradingAgriName(traderID);
	}
	
	public List<Object[]> getSaleRequestByTrader(int traderID){
		return dataRepository.getSaleRequestByTrader(traderID, Constant.ENABLE_STATE);
	}
	
	public List<Sale> getSaleRequestForMaps(int traderID){
		return dataRepository.getSaleRequestForMaps(traderID, Constant.ENABLE_STATE);
	}
	
	public List<Object[]> getSaleSelectedByTrader(int traderID){
		return dataRepository.getSaleSelectedByTrader(traderID, Constant.SELECTED_STATE);
	}
	
	public List<Sale> getSaleSelectedForMaps(int traderID){
		return dataRepository.getSaleSelectedForMaps(traderID, Constant.SELECTED_STATE);
	}
	
	public List<TradingData> getSaleSelectedByUser(int userID){
		return dataRepository.getSaleSelectedByUser(userID, Constant.SELECTED_STATE);
	}
	
	public List<TradingData> getSaleRequest(int saleID){
		return dataRepository.getSaleRequest(saleID, Constant.ENABLE_STATE);
	}

	public List<Object[]> getAgriForChar(int traderID){
		return dataRepository.getAgriForChar(traderID);
	}
	
	public List<Object[]> getCommuneForChar(int traderID){
		List<Object[]> communes = dataRepository.getCommuneForChar(traderID);
		
		for (Object[] objects : communes) {
			objects[0] = addressService.getCommuneName((int)objects[0]);
		}
		
		return communes;
	}
	
	public List<PieChart> getAreaForChar(int traderID){
		List<Mining> minings = miningRepository.getAllMiningByTrader(traderID);
		HashMap<Integer, Integer> sum = minings.get(0).getArea();
		minings.remove(0);
		List<PieChart> result = new ArrayList<>();
		
		for (Mining mining : minings) {
			HashMap<Integer, Integer> area = mining.getArea();
			area.forEach((k,v) -> {
				int value = sum.containsKey(k)? sum.get(k) + v : v;
				sum.put(k, value);
			});
		}
		
		sum.forEach((k,v) -> {
			int kk = k+1;
			String name = k.toString() + "-" + kk;
			
			result.add(new PieChart(name,v));
		});
		
		return result;
	}
	
	public void createSaleRequest(Trader trader, int saleID){
		TradingData tradingData = new TradingData(trader, new Sale(saleID), Constant.CURRENT_DATE(),
				null, 1);
		dataRepository.save(tradingData);
		
		saleRepository.RequestCountUp(saleID);
		
		User user = saleRepository.getUser(saleID);
		userService.createNotification(saleID, Constant.U_SALE_REQUEST, user, trader);
		
		updateMining(trader.getId(), saleID);
	}
	
	public void createSaleRequestForMining(int traderID, int saleID){
		TradingData tradingData = new TradingData(new Trader(traderID), new Sale(saleID), Constant.CURRENT_DATE(),
				null, 1);
		dataRepository.save(tradingData);
		
		saleRepository.RequestCountUp(saleID);
	}
	
	public void cancelSaleRequest(int dataID){
		dataRepository.updateState(dataID, Constant.DISABLE_STATE);
	}
	
	public int confirmRequest(int requestID, User user){
		int saleID = dataRepository.getSaleID(requestID);
		saleRepository.updateState(Constant.SELECTED_STATE, saleID);
		
		dataRepository.updateStateBySaleID(saleID, Constant.DISABLE_STATE);
		
		TradingData tradingData = dataRepository.findOne(requestID);
		tradingData.setDateSelected(Constant.CURRENT_DATE());
		tradingData.setStatus(Constant.SELECTED_STATE);
		dataRepository.save(tradingData);

		traderService.createNotification(requestID, Constant.T_CONFIRM_REQUEST, user, tradingData.getTrader());
		
		return saleID;
	}
	
	public void createNotifyRestore(int saleID, User user){
		List<Trader> traders = dataRepository.getListTraderRequest(saleID, Constant.ENABLE_STATE);
		
		for (Trader trader : traders) {
			traderService.createNotification(saleID, Constant.T_SALE_RESTORE, user, trader);
		}
	}
	
	public void restoreRequest (int saleID, User user){
		dataRepository.updateStateBySaleID(saleID, Constant.ENABLE_STATE);
		saleRepository.updateState(Constant.ENABLE_STATE, saleID);
		
		createNotifyRestore(saleID, user);
	}
	
	public List<Object[]> getInfoForMaps(){
		return saleRepository.getInfoForMaps();
	}
	
	public boolean checkDeleteAgri(int id){
		int count = saleRepository.countAgriSale(id, Constant.ENABLE_STATE);
		return count > 0? true : false; 
	}
	
	public void updateSaleExpired(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, Constant.SALE_EXPERID);
		Date Experid = new Date(cal.getTime().getTime());
		saleRepository.updateSaleExpired(Experid);
	}
	
	public Address getAddress(int saleID){
		return saleRepository.getAddress(saleID);
	}
	
	public List<Sale> getSaleForExcel(){
		return saleRepository.getSaleForExcel();
	}
	
	
	public List<SaleScore> recommentSale(int traderID){
		List<Sale> sales = getSaleListByTrader(traderID);
		int agriID = sales.get(0).getAgriculture().getId();
		
		Mining mining = miningRepository.getMiningByTrader(traderID, agriID);
		if(mining == null) {
			mining = new Mining();
		}
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> quanlitys = mining.getQuanlity();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();
		ArrayList<SaleScore> scores = new ArrayList<>();

		// list sale sort by id. this var to detect when id change
		int currID = agriID;
		
		int area, quanlity, communeID;
		int areaScore, quanScore, commScore;
		
		
		
		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			int id = sale.getAgriculture().getId();
			if(id == currID){
				area = (int)sale.getArea();
				quanlity = (int)sale.getQuantity()/4;
				communeID = sale.getCommuneID();
				
				areaScore = areas.get(area) == null ? 0 : areas.get(area);
				quanScore = quanlitys.get(quanlity) == null ? 0 : quanlitys.get(quanlity);
				commScore = communeIDs.get(communeID) == null ? 0 : communeIDs.get(communeID);
				int score = areaScore + commScore;
				
				scores.add(new SaleScore(sale, score));
			}else{
				currID = sale.getAgriculture().getId();
				
				mining = miningRepository.getMiningByTrader(traderID, currID);
				if(mining == null) {
					mining = new Mining();
				}
				areas = mining.getArea();
				quanlitys = mining.getQuanlity();
				communeIDs = mining.getCommuneID();
				
				i--;
			}
		}
		
		Collections.sort(scores);
		Collections.reverse(scores);

		return scores;
	}
	
	public void updateMining(int traderID, int saleID){
		/*Sale sale = saleRepository.getSaleForUpdateMining(saleID);
		
		// convent to hashmap
		Mining mining = miningRepository.getMiningByTrader(traderID);
		if(mining == null){
			System.out.println("null");
		}
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> quanlitys = mining.getQuanlity();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();
		
		int area = (int)sale.getArea();
		int quanlity = (int)sale.getQuantity()/4;
		int communeID = sale.getCommuneID();
		
		// recount score
		int areaScore = areas.get(area) == null ? 1 : areas.get(area) + 1;
		int quanScore = quanlitys.get(quanlity) == null ? 1 : quanlitys.get(quanlity) + 1;
		int commScore = communeIDs.get(communeID) == null ? 1 : communeIDs.get(communeID) + 1;
		
		// update new 
		 * 
		 * score to hashmap
		areas.put(area, areaScore);
		quanlitys.put(quanlity, quanScore);
		communeIDs.put(communeID, commScore);
		
		miningRepository.save(mining);*/
	}
	
	public void updateAllMining(int traderID){
		List<Sale> sales = dataRepository.getSaleForMining(traderID);
		Trader trader = new Trader(traderID);
		
		HashMap<Integer, Integer> areas = new HashMap<>();
		HashMap<Integer, Integer> quanlitys = new HashMap<>();
		HashMap<Integer, Integer> communeIDs = new HashMap<>();
		
		// list sale sort by id. this var to detect when id change
		int currID = sales.get(0).getAgriculture().getId();
		
		int area, quanlity, communeID, agriID;
		int areaScore, quanScore, commScore;
		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			agriID = sale.getAgriculture().getId();
			
			if(agriID == currID){ // id not change
				area = (int)sale.getArea();
				quanlity = (int)sale.getQuantity()/4;
				communeID = sale.getCommuneID();
				
				// recount score
				areaScore = areas.get(area) == null ? 1 : areas.get(area) + 1;
				quanScore = quanlitys.get(quanlity) == null ? 1 : quanlitys.get(quanlity) + 1;
				commScore = communeIDs.get(communeID) == null ? 1 : communeIDs.get(communeID) + 1;
				
				// update new score to hashmap
				areas.put(area, areaScore);
				quanlitys.put(quanlity, quanScore);
				communeIDs.put(communeID, commScore);
			}else{ // id change
				
				// save mining
				Mining mining = new Mining();
				mining.update(areas, quanlitys, communeIDs, currID, trader);
				miningRepository.save(mining);
				
				// init hashmap to store next id in list
				areas = new HashMap<>();
				quanlitys = new HashMap<>();
				communeIDs = new HashMap<>();
				
				currID = sale.getAgriculture().getId();
				i--;
			}
		}
	}
}
