package com.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.constant.Constant;

@Entity
@Table(name="Trader")
public class Trader implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="name", length = Constant.COLUMN_LENGTH_30_LIMIT)
    private String name;
    
    @Column(name="age")    
    private int age;
    
    @Column(name="ratingSum")    
    private float ratingSum;
    
    @Column(name="phoneNum", length = Constant.COLUMN_LENGTH_20_LIMIT)    
    private String phoneNum;
    
    @Column(name="dateCreate") 
    private Date dateCreate;
    
    @OneToOne()
    @JoinColumn(name = "account", referencedColumnName = "username")
    private Account account;
    
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;
    
    @OneToMany(mappedBy = "trader")
    private Set<Mining> miningSet = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TradingAgri> tradingSet = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TradingData> requestSet = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<UserCmt> userCmts = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TraderCmt> traderCmts = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserNotification> userNotifications = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TraderNotification> traderNotifications = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TraderReaction> reactions = new HashSet<>();

    ////////////////////////////////

    public Trader(){
    	this.ratingSum = 0;
    }
    
	public Trader(int id) {
		super();
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getRatingSum() {
		return ratingSum;
	}

	public void setRatingSum(float ratingSum) {
		this.ratingSum = ratingSum;
	}

	public int getId() {
		return id;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBirthYear() {
		return age;
	}

	public void setBirthYear(int birthYear) {
		this.age = birthYear;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Set<TradingAgri> getTradingSet() {
		return tradingSet;
	}

	public void setTradingSet(Set<TradingAgri> tradingSet) {
		this.tradingSet = tradingSet;
	}

	public Set<TradingData> getRequestSet() {
		return requestSet;
	}

	public void setRequestSet(Set<TradingData> requestSet) {
		this.requestSet = requestSet;
	}

	public Set<UserCmt> getUserCmts() {
		return userCmts;
	}

	public void setUserCmts(Set<UserCmt> userCmts) {
		this.userCmts = userCmts;
	}

	public Set<TraderCmt> getTraderCmts() {
		return traderCmts;
	}

	public void setTraderCmts(Set<TraderCmt> traderCmts) {
		this.traderCmts = traderCmts;
	}

	public Set<TraderReaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<TraderReaction> reactions) {
		this.reactions = reactions;
	}

	public Set<UserNotification> getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(Set<UserNotification> userNotifications) {
		this.userNotifications = userNotifications;
	}

	public Set<TraderNotification> getTraderNotifications() {
		return traderNotifications;
	}

	public void setTraderNotifications(Set<TraderNotification> traderNotifications) {
		this.traderNotifications = traderNotifications;
	}

	public Set<Mining> getMiningSet() {
		return miningSet;
	}

	public void setMiningSet(Set<Mining> miningSet) {
		this.miningSet = miningSet;
	}
	
	
}