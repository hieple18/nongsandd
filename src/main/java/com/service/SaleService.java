package com.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
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
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

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
	
	public boolean checkToCreateSale(int userID){
		int month = Constant.CURRENT_MONTH();
		int count = saleRepository.countSalePerMonth(userID, month);
		return (count < Constant.SALE_LIMIT) ? true : false;
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
			
			sendVerifyCode(traderToConfirm.getName() + " đã hủy tin đã mua của bạn, vui lòng vào hệ thống xác nhận", user.getPhoneNum());
			
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
	
	public List<Object[]> getSaleSelectedByUser(int userID){
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
			objects[0] = Constant.GET_COMMUNE_SHORT_NAME((int)objects[0]);
		}
		
		return communes;
	}
	
	public List<PieChart> getAreaForChar(int traderID){
		Mining mining = miningRepository.getMiningByTrader(traderID);
		List<PieChart> result = new ArrayList<>();
		HashMap<Integer, Integer> map = mining.getArea();
		
		/*List<Mining> minings = miningRepository.getAllMiningByTrader(traderID);
		List<PieChart> result = new ArrayList<>();
		HashMap<Integer, Integer> sum = minings.get(0).getArea();
		minings.remove(0);*/
		
		/*for (Mining mining : minings) {
			HashMap<Integer, Integer> area = mining.getArea();
			area.forEach((k,v) -> {
				int value = sum.containsKey(k)? sum.get(k) + v : v;
				sum.put(k, value);
			});
		}*/
		
		map.forEach((k,v) -> {
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
		
		String content = Constant.GET_USER_NOTIF_CONTENT(Constant.U_SALE_REQUEST, trader.getName());
		sendVerifyCode(content, user.getPhoneNum());
		
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
		
		Sale sale = dataRepository.getSale(dataID);
		
		int count = sale.getRequestCount() - 1;
		sale.setRequestCount(count);
		if(count == 0){
			sale.setStatus(Constant.ENABLE_STATE);
		}
		saleRepository.save(sale);
	}
	
	public int confirmRequest(int requestID, User user){
		int saleID = dataRepository.getSaleID(requestID);
		saleRepository.updateState(Constant.SELECTED_STATE, saleID);
		
		dataRepository.updateStateBySaleID(saleID, Constant.DISABLE_STATE);
		
		TradingData tradingData = dataRepository.findOne(requestID);
		tradingData.setDateSelected(Constant.CURRENT_DATE());
		tradingData.setStatus(Constant.SELECTED_STATE);
		dataRepository.save(tradingData);

		String traderPhone = dataRepository.getTraderPhone(requestID);
		String content = Constant.GET_TRADER_NOTIF_CONTENT(Constant.T_CONFIRM_REQUEST, user.getName());
		sendVerifyCode(content, traderPhone);
		
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
		cal.add(Calendar.DATE, -Constant.SALE_EXPERID);
		Date Experid = new Date(cal.getTime().getTime());
		saleRepository.updateSaleExpired(Experid);
	}
	
	public Address getAddress(int saleID){
		return saleRepository.getAddress(saleID);
	}
	
	public List<Sale> getSaleForExcel(){
		return saleRepository.getSaleForExcel();
	}
	
	public int getMaxForMining(HashMap<Integer, Integer> map){
		int max = 0;
		for (Entry<Integer, Integer> index : map.entrySet()) {
			if(index.getValue() > max){
				max = index.getValue();
			}
		}
		
		return max;
	}
	
	// calculate X value for recommend
	public HashMap<Integer, Float> caculateXForMining(HashMap<Integer, Integer> areas, HashMap<Integer, Integer> ids, 
    		HashMap<Integer, Integer> communeIDs, int sum_new){
		int maxArea = Collections.max(areas.values());
		int maxID = Collections.max(ids.values());
		int maxCommune = Collections.max(communeIDs.values());

		
		float sum = maxArea + maxCommune + maxID;
		float M_id = (float)maxID/sum;
		float M_area = (float)maxArea/sum;
		float M_comm = (float)maxCommune/sum;
		
		HashMap<Integer, Float> result = new HashMap<>();
		result.put(Constant.AGRI_M, M_id);
		result.put(Constant.AREA_M, M_area);
		result.put(Constant.COMMUNE_M, M_comm);
		
		return result;
	}
	
	public List<SaleScore> recommendSale(int traderID){
		List<Sale> sales = getSaleListByTrader(traderID);
		ArrayList<SaleScore> scores = new ArrayList<>();
		
		// if trader do not have any info to recommend then return list order by date create
		Mining mining = miningRepository.getMiningByTrader(traderID);
		if(mining == null || mining.isEmpty()) {
			for (Sale sale : sales) {
				scores.add(new SaleScore(sale, 0));
			}
			
			return scores;
		}
		
		// if trader have info to run mining
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> agris = mining.getAgri();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();
		
		//get X value
		int sum = dataRepository.getCountSale(traderID);
		HashMap<Integer, Float> X = caculateXForMining(areas, agris, communeIDs, sum);
		float X_area = X.get(Constant.AREA_M);
		float X_id = X.get(Constant.AGRI_M);
		float X_comm = X.get(Constant.COMMUNE_M);
		
		int area, communeID, id;
		int areaScore, idScore, commScore;

		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			area = (int)sale.getArea();
			id = sale.getAgriculture().getId();
			communeID = sale.getCommuneID();	
			
			areaScore = areas.get(area) == null ? 0 : areas.get(area);
			idScore = agris.get(id) == null ? 0 : agris.get(id);
			commScore = communeIDs.get(communeID) == null ? 0 : communeIDs.get(communeID);
			int score = (int) (X_area*areaScore + X_comm*commScore + X_id*idScore);
			
			scores.add(new SaleScore(sale, score));
		}
		
		Collections.sort(scores);
		Collections.reverse(scores);

		return scores;
	}
	
	public void updateMining(int traderID, int saleID){
		Sale sale = saleRepository.findOne(saleID);
		Trader trader = new Trader(traderID);
		
		Mining mining = miningRepository.getMiningByTrader(traderID);
		
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> agris = mining.getAgri();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();

		
		int area, id, communeID;
		int areaScore, quanScore, commScore;
		
		area = (int)sale.getArea();
		id = sale.getAgriculture().getId();
		communeID = sale.getCommuneID();
		
		// recount score
		areaScore = areas.get(area) == null ? 1 : areas.get(area) + 1;
		quanScore = agris.get(id) == null ? 1 : agris.get(id) + 1;
		commScore = communeIDs.get(communeID) == null ? 1 : communeIDs.get(communeID) + 1;
		
		// update new score to hashmap
		areas.put(area, areaScore);
		agris.put(id, quanScore);
		communeIDs.put(communeID, commScore);
		
		// save mining
		mining.update(areas, agris, communeIDs, trader);
		miningRepository.save(mining);
	}
	
	public void updateAllMining(int traderID){
		List<Sale> sales = dataRepository.getSaleForMining(traderID);
		Trader trader = new Trader(traderID);
		
		Mining mining = miningRepository.getMiningByTrader(traderID);
		
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> agris = mining.getAgri();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();

		int area, id, communeID;
		int areaScore, quanScore, commScore;
		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			area = (int)sale.getArea();
			id = sale.getAgriculture().getId();
			communeID = sale.getCommuneID();
			
			// recount score
			areaScore = areas.get(area) == null ? 1 : areas.get(area) + 1;
			quanScore = agris.get(id) == null ? 1 : agris.get(id) + 1;
			commScore = communeIDs.get(communeID) == null ? 1 : communeIDs.get(communeID) + 1;
			
			// update new score to hashmap
			areas.put(area, areaScore);
			agris.put(id, quanScore);
			communeIDs.put(communeID, commScore);
		}
		
		// save mining
		mining.update(areas, agris, communeIDs, trader);
		miningRepository.save(mining);
	}
	
	public String sendVerifyCode(String content, String phoneNum){
		String phone = "+84" + phoneNum.substring(1);
		
    	try {
            TwilioRestClient client = new TwilioRestClient(Constant.ACCOUNT_SID, Constant.AUTH_TOKEN);
     
            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", content));
            params.add(new BasicNameValuePair("To", phone)); //Add real number here
            params.add(new BasicNameValuePair("From", Constant.TWILIO_NUMBER));
     
            MessageFactory messageFactory = client.getAccount().getMessageFactory();
            Message message = messageFactory.create(params);
            System.out.println(message.getSid());
            return "good";
        }
        catch (TwilioRestException e) {
            return e.toString();
        }
    }
	
	// this function using for recommend sale for trader with distinguish agriculture id
	/*public List<SaleScore> miningWithAgriID(int traderID){
		List<Sale> sales = getSaleListByTrader(traderID);
		int agriID = sales.get(0).getAgriculture().getId();
		ArrayList<SaleScore> scores = new ArrayList<>();
		
		// if trader do not have any info to recommend then return list order by date create
		Mining mining = miningRepository.getMiningByTrader(traderID, agriID);
		if(mining == null || mining.isEmpty()) {
			for (Sale sale : sales) {
				scores.add(new SaleScore(sale, 0));
			}
			
			return scores;
		}
		
		// if trader have info to run mining
		HashMap<Integer, Integer> areas = mining.getArea();
		HashMap<Integer, Integer> agris = mining.getAgri();
		HashMap<Integer, Integer> communeIDs = mining.getCommuneID();

		// list sale sort by id. this var to detect when id change
		int currID = agriID;
		
		int area, communeID;
		int areaScore, idScore, commScore;

		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			int id = sale.getAgriculture().getId();
			if(id == currID){
				area = (int)sale.getArea();
				id = sale.getAgriculture().getId();
				communeID = sale.getCommuneID();	
				
				areaScore = areas.get(area) == null ? 0 : areas.get(area);
				idScore = agris.get(id) == null ? 0 : agris.get(id);
				commScore = communeIDs.get(communeID) == null ? 0 : communeIDs.get(communeID);
				int score = areaScore + commScore + idScore;
				
				scores.add(new SaleScore(sale, score));
			}else{
				currID = sale.getAgriculture().getId();
				
				mining = miningRepository.getMiningByTrader(traderID, currID);
				if(mining == null) {
					mining = new Mining();
				}
				areas = mining.getArea();
				agris = mining.getAgri();
				communeIDs = mining.getCommuneID();
				
				i--;
			}
		}
		
		Collections.sort(scores);
		Collections.reverse(scores);

		return scores;
	}
	
	// this function using for recommend sale for trader with distinguish agriculture id
	public void updateWithAgriID(int traderID){
		List<Sale> sales = dataRepository.getSaleForMining(traderID);
		Trader trader = new Trader(traderID);
		
		HashMap<Integer, Integer> areas = new HashMap<>();
		HashMap<Integer, Integer> agris = new HashMap<>();
		HashMap<Integer, Integer> communeIDs = new HashMap<>();
		
		// list sale sort by id. this var to detect when id change
		int currID = sales.get(0).getAgriculture().getId();
		
		int area, id, communeID, agriID;
		int areaScore, quanScore, commScore;
		for(int i = 0; i < sales.size(); i++){
			Sale sale = sales.get(i);
			agriID = sale.getAgriculture().getId();
			
			if(agriID == currID){ // id not change
				area = (int)sale.getArea();
				id = sale.getAgriculture().getId();
				communeID = sale.getCommuneID();
				
				// recount score
				areaScore = areas.get(area) == null ? 1 : areas.get(area) + 1;
				quanScore = agris.get(id) == null ? 1 : agris.get(id) + 1;
				commScore = communeIDs.get(communeID) == null ? 1 : communeIDs.get(communeID) + 1;
				
				// update new score to hashmap
				areas.put(area, areaScore);
				agris.put(id, quanScore);
				communeIDs.put(communeID, commScore);
			}else{ // id change
				
				// save mining
				Mining mining = new Mining();
				mining.update(areas, agris, communeIDs, currID, trader);
				miningRepository.save(mining);
				
				// init hashmap to store next id in list
				areas = new HashMap<>();
				agris = new HashMap<>();
				communeIDs = new HashMap<>();
				
				currID = sale.getAgriculture().getId();
				i--;
			}
		}
	}*/
}
