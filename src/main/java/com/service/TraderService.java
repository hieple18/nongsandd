package com.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.constant.Constant;
import com.entity.Account;
import com.entity.Address;
import com.entity.Agriculture;
import com.entity.Mining;
import com.entity.TempAccount;
import com.entity.Trader;
import com.entity.TraderCmt;
import com.entity.TraderNotification;
import com.entity.TradingAgri;
import com.entity.User;
import com.reponsitory.AccountRepository;
import com.reponsitory.AgricultureRepository;
import com.reponsitory.MiningRepository;
import com.reponsitory.TempAccRepository;
import com.reponsitory.TraderCmtRepository;
import com.reponsitory.TraderNotifyRepository;
import com.reponsitory.TraderRepository;
import com.reponsitory.TradingAgriRepository;
import com.reponsitory.TradingDataRepository;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

/**
 * @author: HiepLe
 * @version: Mar 16, 2018
 */

@Service
public class TraderService {
	
	@Autowired
	private AccountRepository accountReponsitory;
	
	@Autowired
    private AddressService addressService;
	
	@Autowired
    private TraderRepository traderRepository; 
	
	@Autowired
	private TraderNotifyRepository notifyRepository;
	
	@Autowired
    private TradingAgriRepository tradingAgriRepository;
	
	@Autowired
    private TradingDataRepository tradingDataRepository;
	
	@Autowired
	private TraderCmtRepository traderCmtRepository;
	
	@Autowired
    private AgricultureRepository agricultureRepository;
	
	@Autowired
    private MiningRepository miningRepository;
	
	@Autowired
	private TempAccRepository tempAccRepository;
	
	public List<Trader> getListTrader(){
		return traderRepository.findAll();
	}
	
	public int createVerifyCode(){
		Random r = new Random();
		int code = 1000 + r.nextInt(8999);
		return code;
	}
	
	public int createTempTrader(Trader trader, int hamletID, String tradingList, boolean initMinig){
		// create account
		String password = trader.getAccount().getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account account = new Account(trader.getPhoneNum(), encoder.encode(password),
				Constant.ROLE_TRADER, 0);
		accountReponsitory.save(account);
		trader.setAccount(account);
		
		//create address
		addressService.createAddress(trader.getAddress(), hamletID);
		
		//create trader
	    trader.setDateCreate(Constant.CURRENT_DATE());
	    trader.setCode(createVerifyCode());
		trader.setStatus(Constant.NOT_VERIFY);
	    traderRepository.save(trader);
	    
	    if(initMinig){ // if create new. then init mining for trader.
		    Mining mining = new Mining();
		    mining.setTrader(trader);
		    miningRepository.save(mining);
	    }
	    
	    //create trading agriculture
	    createTradingAgri(tradingList, trader);
	    
	    sendVerifyCode(trader.getCode(), trader.getPhoneNum());

	    return trader.getId();
	}
	
	public int checkToCreateTrader(Trader trader, int hamletID, String tradingList){
		Trader verified = traderRepository.getVerifiedTrader(trader.getPhoneNum(), Constant.WAIT_TO_REGISTER);
		if(verified != null){
			trader.setId(verified.getId());
			return createTempTrader(trader, hamletID, tradingList, true);
		}
		return -1;
	}
	
	public int updateTraderNotActive(Trader trader, int hamletID, String tradingList){
		Trader old = traderRepository.getTraderByPhone(trader.getPhoneNum());
		if(old == null || old.getStatus() != Constant.NOT_VERIFY){
			return -1;
		}
		
		trader.setId(old.getId());
		tradingAgriRepository.deleteByTraderID(trader.getId());
	    
	    return createTempTrader(trader, hamletID, tradingList, false);
	}
	
	public int activeAccount(String phoneNum){
		Trader Trader = traderRepository.getTraderByPhone(phoneNum);
		if(Trader != null){
			if(Trader.getStatus() == Constant.NOT_VERIFY){
				Trader.setCode(createVerifyCode());
				traderRepository.save(Trader);
				sendVerifyCode(Trader.getCode(), Trader.getPhoneNum());
				return Trader.getId();
			}
		}
		
		return -1;
	}
	
	public String sendVerifyCode(int code, String phoneNum){
		String phone = "+84" + phoneNum.substring(1);
		
    	try {
            TwilioRestClient client = new TwilioRestClient(Constant.ACCOUNT_SID, Constant.AUTH_TOKEN);
     
            // Build a filter for the MessageList
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Body", "NongSanDD thong bao ma xac thuc cua ban la: " + code));
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
	
	public boolean verifyTrader(int id, int code){
		Trader trader = traderRepository.findOne(id);
		if(trader != null){
			if(trader.getCode() == code){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verifyAccount(int id, int code){
		Trader trader = traderRepository.findOne(id);
		if(trader != null && trader.getStatus() == Constant.NOT_VERIFY){
			if(trader.getCode() == code){
				Account account = accountReponsitory.findOne(trader.getPhoneNum());
				
				if(account != null){
					traderRepository.updateStatus(1, id);
					
					account.setEnabled(1);
					accountReponsitory.save(account);
					return true;
				}
				
				return false;
			}
		}
		
		return false;
	}
	
	public void resendVerify(int id){
		Trader Trader = traderRepository.findOne(id);
		if(Trader != null){
			if(Trader.getStatus() == Constant.NOT_VERIFY){
				Trader.setCode(createVerifyCode());
				traderRepository.save(Trader);
				sendVerifyCode(Trader.getCode(), Trader.getPhoneNum());
			}
		}
	}
	
	public Trader getTraderNotActive(String phoneNum) {
		return traderRepository.getTraderNotActive(phoneNum, Constant.NOT_VERIFY);
	}
	
	public boolean changePass(String phoneNum, String pass){
		Account account = accountReponsitory.getActiveAccount(phoneNum, Constant.ROLE_TRADER);
		if(account != null){
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			TempAccount temp = new TempAccount(phoneNum, encoder.encode(pass));
			temp.setCode(createVerifyCode());
			
			TempAccount old = tempAccRepository.getTemp(phoneNum);
			if(old != null) // if trader change pass before. so exists a tempAccount. then replace it 
				temp.setId(old.getId());
			tempAccRepository.save(temp);
			
			sendVerifyCode(temp.getCode(), phoneNum);
			return true;
		}
		
		return false;
	}
	
	public boolean confirmChangePass(String phone, int code){
		Account account = accountReponsitory.getActiveAccount(phone, Constant.ROLE_TRADER);
		if(account != null){
			TempAccount tempAccount = tempAccRepository.getTemp(account.getUserName());
			
			if(tempAccount != null){
				if(code == tempAccount.getCode()){
					account.setPassword(tempAccount.getPassword());
					accountReponsitory.save(account);
					
					tempAccRepository.delete(tempAccount.getId());
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
		
		return false;
	}
	
	public int checkPhoneNum(String phoneNum) {
		Account account = accountReponsitory.getAccountByPhone(phoneNum, Constant.ROLE_TRADER);
		
		if(account != null){
			if(account.getEnabled() == 1) // this account activated
				return 1;
			else // this account did not active
				return 0;
		}
		
		// this phone number do not have account
		return -1;
	}
	
	public int checkVerifyPhoneNum(String phoneNum) {
		Trader trader = traderRepository.getTraderByPhone(phoneNum);

		if(trader != null){
			return trader.getStatus();
		}
		
		// this phone number do not have account
		return -2;
	}
	
	public void updateProfile(Trader trader, Trader oldTrader, int hamletID, String tradingList){
		trader.setId(oldTrader.getId());
		trader.setDateCreate(oldTrader.getDateCreate());
		trader.setRatingSum(oldTrader.getRatingSum());
		trader.setAccount(oldTrader.getAccount());
		
		//create address
		addressService.createAddress(trader.getAddress(), hamletID);
		
		//create trader
	    traderRepository.save(trader);
	    
	    //create trading agriculture
	    updateTradingAgri(tradingList, trader);
	}
	
	public void createNotification(int id, int status, User user, Trader trader) {
		String content = Constant.GET_TRADER_NOTIF_CONTENT(status, user.getName());
		String link = Constant.GET_TRADER_NOTIF_LINK(status, id);
		
		TraderNotification notification = new TraderNotification(trader, user, content, link,
				Constant.DATE_IN_MILLISECONDS(), status);
		notifyRepository.save(notification);
	}
	
	public void createTradingAgri(String tradingList, Trader trader){
		Agriculture agriculture = new Agriculture();
		
		String[] parts = tradingList.split(",");
		for (String value : parts) {
			agriculture = agricultureRepository.findOne(Integer.parseInt(value));
			TradingAgri tradingAgri = new TradingAgri(trader, agriculture);
			
			tradingAgriRepository.save(tradingAgri);
		}
	}
	
	public void updateTradingAgri(String tradingList, Trader trader){
		tradingAgriRepository.deleteByTraderID(trader.getId());
		createTradingAgri(tradingList, trader);
	}
	
	public int getTraderIdByPhoneNum(String phoneNum){
		return traderRepository.getTraderIDByPhone(phoneNum);
	}
	
	public Trader getTraderByPhoneNum(String phoneNum){
		return traderRepository.getTraderByPhone(phoneNum);
	}
	
	public String getNameByPhoneNum(String phoneNum){
		return traderRepository.getNameByPhone(phoneNum);
	}
	
	public List<TraderNotification> getNotify(int traderID) {
		List<TraderNotification> notifications = notifyRepository.getNotify(traderID);
    	for (TraderNotification notification : notifications) {
    		long previoustime = notification.getDateCreate();
			notification.setTimeAgo(getDateByMillis(previoustime));
		}
    	
    	return notifications;
	}
	
	public String getDateByMillis(long previoustime){
		long millis = Constant.DATE_IN_MILLISECONDS() - previoustime;
		long minute = (millis / (1000 * 60)) % 60;
		
		String time;
		if(minute > 0){// >1m
			long hour = (minute/60) % 24;
			if(hour > 0){ // > 1h
				time = String.format("%02dh:%02dm", hour, minute);
			}else{
				time = String.format("%02dm", minute);
			}
		}else{
			time = "1m";
		}
		return time;
	}
	
	public void disableNotification(int notifyID){
		notifyRepository.disableNotify(Constant.DISABLE_STATE, notifyID);
	}
	
	public boolean checkConditionToAddCmt(int userID, int traderID){
		Date dateSelected = tradingDataRepository.getNewestSaleSelected(userID, traderID, Constant.SELECTED_STATE);
		if(dateSelected == null){
			return false;
		}else{
			LocalDate fromDate = dateSelected.toLocalDate();
			LocalDate toDate = Constant.CURRENT_DATE().toLocalDate();
			long days = ChronoUnit.DAYS.between(fromDate, toDate);
			
			if(days > 180){
				return false;
			}
			
			return true;
		}
	}
	
	public Trader getTrader(int traderID){
		return traderRepository.findOne(traderID);
	}
	
	public void saveTrader(Trader trader){
		traderRepository.save(trader);
	}
	
	// called from user controller
	public List<TraderCmt> getTraderCmts(int traderID){
		return traderCmtRepository.getCmt(traderID);
	}
	
	// called from user controller
	public void CreateCmtAboutTrader(User user, int traderID, int ratingStar, String content) {
		addCmtAboutTrader(user, traderID, ratingStar, content);
		
		Trader trader = new Trader();
		trader.setId(traderID);
		createNotification(traderID, Constant.T_USER_CMT, user, trader);
	}

	// called from user controller
	public void addCmtAboutTrader(User user, int traderID, int ratingStar, String content) {
		float sum = traderRepository.getRatingSum(traderID);
		int count = traderCmtRepository.getCountCmt(traderID);
		float newSum = (sum + ratingStar) / (count + 1);
		
		traderRepository.updateRatingSum(newSum, traderID);
		Trader trader = traderRepository.findOne(traderID);
		
		TraderCmt cmt = new TraderCmt(trader, user, content, ratingStar, Constant.CURRENT_DATE(),
				Constant.ENABLE_STATE);
		traderCmtRepository.save(cmt);
	}
	
	// called from user controller
	public void updateCmtAboutTrader(int cmtID, User user, int traderID, int ratingStar, String content) {
		int confirmUserID = traderCmtRepository.getUserID(cmtID);
		if(user.getId() == confirmUserID){
			float sum = traderRepository.getRatingSum(traderID);
			int count = traderCmtRepository.getCountCmt(traderID);
			int previousRatingStar = traderCmtRepository.getRatingStar(cmtID);
			float newSum = (sum*count - previousRatingStar + ratingStar)/count;
			traderRepository.updateRatingSum(newSum, traderID);
			Trader trader = traderRepository.findOne(traderID);
			TraderCmt cmt = new TraderCmt(trader, user, content, ratingStar, Constant.CURRENT_DATE(),
					Constant.ENABLE_STATE);
			cmt.setId(cmtID);
			traderCmtRepository.save(cmt);
		}
	}
	
	// called from user controller
		public void deleteCmtAboutTrader(User user, int id) {
			int confirmUserID = traderCmtRepository.getUserID(id);
			if(user.getId() == confirmUserID){
				int traderID = traderCmtRepository.getTraderID(id);
				traderCmtRepository.delete(id);
				
				float newSum = traderCmtRepository.getAvgStar(traderID);
				traderRepository.updateRatingSum(newSum, traderID);
			}
		}
	
	// called from user controller
	public List<Integer> getIDCmtToShowEdit(int userID, int traderID){
		return traderCmtRepository.getIDCmtToShowEdit(userID, traderID);
	}
	
	public List<Object[]> getInfoForMaps(){
		return traderRepository.getInfoForMaps();
	}
	
	public Address getAddress(int traderID){
		return traderRepository.getAddress(traderID);
	}	
	
	public void verifyTraderByAdmin(String phoneNum){
		Trader trader = new Trader();
		trader.setPhoneNum(phoneNum);
		trader.setStatus(Constant.WAIT_TO_REGISTER);
		trader.setDateCreate(Constant.CURRENT_DATE());
		traderRepository.save(trader);
	}
	
	public void updateTraderByAdmin(int id, String phoneNum){
		Trader trader = traderRepository.findOne(id);
		if(trader.getStatus() == Constant.WAIT_TO_REGISTER){
			trader.setPhoneNum(phoneNum);
			traderRepository.save(trader);
		}
	}
	
	public void deleteTraderByAdmin(int id){
		Trader trader = traderRepository.findOne(id);
		if(trader.getStatus() == Constant.WAIT_TO_REGISTER){
			traderRepository.delete(id);
		}
	}
	
	public boolean checkVerifyTraderByAdmin(String phoneNum){
		int count = traderRepository.getTraderWaitToVerify(phoneNum, Constant.WAIT_TO_REGISTER);
		
		return (count == 0) ? false : true;
	}
	
	public List<Trader> getTraderWait(){
		return traderRepository.getTraderWaitToRegister(Constant.WAIT_TO_REGISTER);
	}
}
