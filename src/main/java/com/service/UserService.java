package com.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.constant.Constant;
import com.entity.Account;
import com.entity.Address;
import com.entity.Trader;
import com.entity.User;
import com.entity.UserCmt;
import com.entity.UserNotification;
import com.reponsitory.AccountRepository;
import com.reponsitory.TradingDataRepository;
import com.reponsitory.UserCmtRepository;
import com.reponsitory.UserNotifyRepository;
import com.reponsitory.UserRepository;

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

	public void createNotification(int id, int status, User user, Trader trader) {
		String content = Constant.GET_USER_NOTIF_CONTENT(status, trader.getName());
		String link = Constant.GET_USER_NOTIF_LINK(status, id);
		
		UserNotification notification = new UserNotification(user, trader, content, Constant.DATE_IN_MILLISECONDS(),
				link, status);
		notifyRepository.save(notification);
	}

	public Account getDataByUserName(String userName) {
		return accountReponsitory.getActiveUser(userName);
	}

	public boolean checkPhoneNum(String phoneNum) {
		int count = accountReponsitory.checkPhoneNum(phoneNum);
		return (count == 0) ? true : false;
	}

	public void createUser(User user, int hamletID) {
		String password = user.getAccount().getPassword();

		// create account
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Account account = new Account(user.getPhoneNum(), encoder.encode(password), Constant.ROLE_USER, (short) 1);
		accountReponsitory.save(account);
		user.setAccount(account);

		// create address
		addressService.createAddress(user.getAddress(), hamletID);

		// create user
		user.setDateCreate(Constant.CURRENT_DATE());
		userRepository.save(user);
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
		Date dateSelected = tradingDataRepository.getNewestSaleSelected(userID, traderID, Constant.ENABLE_STATE);
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
}
