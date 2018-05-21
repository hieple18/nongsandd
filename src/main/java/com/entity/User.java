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
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @OneToMany(mappedBy = "user")
    private Set<Sale> saleSet = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserCmt> userCmts = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TraderCmt> traderCmts = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserNotification> userNotifications = new HashSet<>();
    
    @OneToMany(mappedBy = "trader")
    private Set<TraderNotification> traderNotifications = new HashSet<>();
    
    @OneToMany(mappedBy = "user")
    private Set<UserReaction> reactions = new HashSet<>();

    ////////////////////////////
    
    public User(){
    	this.ratingSum = 0;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
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

	public Set<Sale> getSaleSet() {
		return saleSet;
	}

	public void setSaleSet(Set<Sale> saleSet) {
		this.saleSet = saleSet;
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

	public Set<UserReaction> getReactions() {
		return reactions;
	}

	public void setReactions(Set<UserReaction> reactions) {
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
    
    
}