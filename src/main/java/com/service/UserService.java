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
import com.entity.TempAccount;
import com.entity.Trader;
import com.entity.User;
import com.entity.UserCmt;
import com.entity.UserNotification;
import com.reponsitory.AccountRepository;
import com.reponsitory.TempAccRepository;
import com.reponsitory.TradingDataRepository;
import com.reponsitory.UserCmtRepository;
import com.reponsitory.UserNotifyRepository;
import com.reponsitory.UserRepository;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

@Service
public class UserService {

	@Autowired
	private AccountRepository accountReponsitory;

	@Autowired
	private AddressService addressService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserCmtRepository userCmtRepository;

	@Autowired
	private UserNotifyRepository notifyRepository;

	@Autowired
	private TradingDataRepository tradingDataRepository;
	
	@Autowired
	private TempAccRepository tempAccRepository;

	public void createNotification(int id, int status, User user, Trader trader) {
		String content = Constant.GET_USER_NOTIF_CONTENT(status, trader.getName());
		String link = Constant.GET_USER_NOTIF_LINK(status, id);
		
		UserNotification notification = new UserNotification(user, trader, content, Constant.DATE_IN_MILLISECONDS(),
				link, status);
		notifyRepository.save(notification);
	}

	public int createVerifyCode(){
		Random r = new Random();
		int code = 1000 + r.nextInt(8999);
		return code;
	}
	
	public Account getDataByUserName(String userName) {
		return accountReponsitory.getActiveAccount(userName, Constant.ROLE_USER);
	}

	public boolean checkPhoneNum(String phoneNum) {
		Account account = accountReponsitory.getAccountByPhone(phoneNum, Constant.ROLE_USER);
		
		if(account != null){
			if(account.getEnabled() == 1) // this account activated
				return true;
		}
		
		// this phone number do not have account
		return false;
	}

	public boolean changePass(String phoneNum, String pass){
		Account account = accountReponsitory.getActiveAccount(phoneNum, Constant.ROLE_USER);
		if(account != null){
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			TempAccount temp = new TempAccount(phoneNum, encoder.encode(pass));
			temp.setCode(createVerifyCode());
			
			TempAccount old = tempAccRepository.getTemp(phoneNum);
			if(old != null) // if user change pass before. so exists a tempAccount. then replace it 
				temp.setId(old.getId());
			tempAccRepository.save(temp);
			
			sendVerifyCode(temp.getCode(), phoneNum);
			return true;
		}
		
		return false;
	}
	
	public boolean confirmChangePass(String phone, String code){
		Account account = accountReponsitory.getActiveAccount(phone, Constant.ROLE_USER);
		if(account != null){
			TempAccount tempAccount = tempAccRepository.getTemp(account.getUserName());
			
			if(tempAccount != null){
				if(code.equals(tempAccount.getCode())){
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
	
	public int createTempUser(User user, int hamletID){
		User verifyUser = userRepository.getUserByPhone(user.getPhoneNum());
		
		if(verifyUser != null){ // this phone number is already used
			if(verifyUser.getStatus() == Constant.VERIFIED) // this account activated
				return -1;
			else{ // this phone number wait to verify. then create new user with previous id
				user.setId(verifyUser.getId());
				return updateTempUser(user, hamletID);
			}
		}

		// create new account
		String password = user.getAccount().getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account account = new Account(user.getPhoneNum(), encoder.encode(password), Constant.ROLE_USER, 0);
		accountReponsitory.save(account);
		user.setAccount(account);

		// create address
		addressService.createAddress(user.getAddress(), hamletID);

		// create user
		user.setDateCreate(Constant.CURRENT_DATE());
		user.setCode(createVerifyCode());
		user.setStatus(Constant.NOT_VERIFY);
		userRepository.save(user);
		
		sendVerifyCode(user.getCode(), user.getPhoneNum());
		
		return user.getId();
	}
	
	public int updateTempUser(User user, int hamletID){
		// create account
		String password = user.getAccount().getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account account = new Account(user.getPhoneNum(), encoder.encode(password), Constant.ROLE_USER, (short) 0);
		accountReponsitory.save(account);
		user.setAccount(account);

		// create address
		addressService.createAddress(user.getAddress(), hamletID);

		// create user
		user.setDateCreate(Constant.CURRENT_DATE());
		user.setCode(createVerifyCode());
		user.setStatus(Constant.NOT_VERIFY);
		userRepository.save(user);

		sendVerifyCode(user.getCode(), user.getPhoneNum());
		
		return user.getId();
	}
	
	public User getUserNotActive(String phoneNum) {
		return userRepository.getUserNotActive(phoneNum, Constant.NOT_VERIFY);
	}
	
	public int updateUserNotActive(User user, int hamletID) {
		User old = userRepository.getUserByPhone(user.getPhoneNum());
		if(old == null || old.getStatus() != Constant.NOT_VERIFY){
			return -1;
		}
		user.setId(old.getId());
		
		return updateTempUser(user, hamletID);
	}
	
	public boolean verifyUser(int id, int code){
		User user = userRepository.findOne(id);
		if(user != null){
			if(user.getCode() == code){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean verifyUserNAccount(int id, int code){
		User user = userRepository.findOne(id);
		if(user != null){
			if(user.getCode() == code){
				Account account = accountReponsitory.findOne(user.getPhoneNum());
				
				if(account != null){
					account.setEnabled((short) 1);
					user.setStatus(Constant.VERIFIED);
					userRepository.save(user);
					accountReponsitory.save(account);
					return true;
				}
				
				return false;
			}
		}
		
		return false;
	}
	
	public void resendVerify(int id){
		User user = userRepository.findOne(id);
		if(user != null){
			if(user.getStatus() == Constant.NOT_VERIFY){
				user.setCode(createVerifyCode());
				userRepository.save(user);
				sendVerifyCode(user.getCode(), user.getPhoneNum());
			}
		}
	}

	public int activeAccount(String phoneNum){
		User user = userRepository.getUserByPhone(phoneNum);
		if(user != null){
			if(user.getStatus() == Constant.NOT_VERIFY){
				user.setCode(createVerifyCode());
				userRepository.save(user);
				sendVerifyCode(user.getCode(), user.getPhoneNum());
				return user.getId();
			}
		}
		
		return -1;
	}
	
	public void updateUser(User user, User oldUser, int hamletID) {
		user.setId(oldUser.getId());
		user.setDateCreate(oldUser.getDateCreate());
		user.setRatingSum(oldUser.getRatingSum());
		user.setAccount(oldUser.getAccount());
		
		// create address
		addressService.createAddress(user.getAddress(), hamletID);

		// create user
		userRepository.save(user);
	}
	
	public int getUserIdByPhoneNum(String phoneNum) {
		return userRepository.getUserIDByPhone(phoneNum);
	}

	public User getUserByPhoneNum(String phoneNum) {
		return userRepository.getUserByPhone(phoneNum);
	}
	
	public String getNameByPhoneNum(String phoneNum) {
		return userRepository.getNameByPhone(phoneNum);
	}

	public User getUserByID(int userID) {
		return userRepository.findOne(userID);
	}

	public List<UserNotification> getNotify(int userID) {
		List<UserNotification> notifications = notifyRepository.getNotify(userID);
		for (UserNotification notification : notifications) {
			long previoustime = notification.getDateCreate();
			notification.setTimeAgo(getDateByMillis(previoustime));
		}

		return notifications;
	}

	public String getDateByMillis(long previoustime) {
		long millis = Constant.DATE_IN_MILLISECONDS() - previoustime;
		long minute = (millis / (1000 * 60)) % 60;

		String time;
		if (minute > 0) {// >1m
			long hour = (minute / 60) % 24;
			if (hour > 0) { // > 1h
				time = String.format("%02dh:%02dm", hour, minute);
			} else {
				time = String.format("%02dm", minute);
			}
		} else {
			time = "1m";
		}
		return time;
	}

	public boolean checkConditionToAddCmt(int userID, int traderID) {
		Date dateSelected = tradingDataRepository.getNewestSaleSelected(userID, traderID, Constant.SELECTED_STATE);
		if (dateSelected == null) {
			return false;
		} else {
			LocalDate fromDate = dateSelected.toLocalDate();
			LocalDate toDate = Constant.CURRENT_DATE().toLocalDate();
			long days = ChronoUnit.DAYS.between(fromDate, toDate);

			if (days > 180) {
				return false;
			}

			return true;
		}
	}

	public void disableNotification(int notifyID) {
		notifyRepository.disableNotify(Constant.DISABLE_STATE, notifyID);
	}

	public List<UserCmt> getUserCmts(int userID) {
		return userCmtRepository.getCmt(userID);
	}

	public void CreateCmtAboutUser(Trader trader, int userID, int ratingStar, String content){
		addCmtAboutUser(trader, userID, ratingStar, content);
		
		User user = new User(); user.setId(userID);
		createNotification(userID, Constant.U_TRADER_CMT, user, trader);
	}
	
	public void addCmtAboutUser(Trader trader, int userID, int ratingStar, String content){
		float sum = userRepository.getRatingSum(userID);
		int count = userCmtRepository.getCountCmt(userID);
		float newSum = (sum + ratingStar)/(count + 1); 
		userRepository.updateRatingSum(newSum, userID);
		User user = userRepository.findOne(userID);
		UserCmt cmt = new UserCmt(trader, user, content, ratingStar, Constant.CURRENT_DATE(), Constant.ENABLE_STATE);
		userCmtRepository.save(cmt);
	}
	
	public void updateCmtAboutTrader(int cmtID, Trader trader, int traderID, int ratingStar, String content) {
		int confirmUserID = userCmtRepository.getTraderID(cmtID);
		if(trader.getId() == confirmUserID){
			float sum = userRepository.getRatingSum(traderID);
			int count = userCmtRepository.getCountCmt(traderID);
			int previousRatingStar = userCmtRepository.getRatingStar(cmtID);
			float newSum = (sum*count - previousRatingStar + ratingStar)/count;
			userRepository.updateRatingSum(newSum, traderID);
			User user = userRepository.findOne(traderID);
			UserCmt cmt = new UserCmt(trader, user, content, ratingStar, Constant.CURRENT_DATE(),
					Constant.ENABLE_STATE);
			cmt.setId(cmtID);
			userCmtRepository.save(cmt);
		}
	}
	
	public List<Integer> getIDCmtToShowEdit(int userID, int traderID){
		return userCmtRepository.getIDCmtToShowEdit(userID, traderID);
	}
	
	public List<Object[]> getInfoForMaps(){
		return userRepository.getInfoForMaps();
	}
	
	public List<User> getListUser(){
		return userRepository.findAll();
	}
	
	public int getMaxID(){
		return userRepository.getMaxID();
	}
	
	public Address getAddress(int userID){
		return userRepository.getAddress(userID);
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
}
