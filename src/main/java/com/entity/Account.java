package com.entity;
/**
*@author HiepLe
*@version 1.0 Nov 14, 2017
*/

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.constant.Constant;

@Entity
@Table(name="Account")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name="username", length = Constant.COLUMN_LENGTH_20_LIMIT)
    private String userName;
    
    @Column(name="password", length = Constant.COLUMN_LENGTH_100_LIMIT)
    private String password;
    
    @Column(name="role", length = Constant.COLUMN_LENGTH_20_LIMIT)    
    private String role;
    
    @Column(name="enabled") 
    private short enabled;
    
    @OneToOne(fetch = FetchType.LAZY,
    cascade =  CascadeType.ALL,
    mappedBy = "account")
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY,
    	    cascade =  CascadeType.ALL,
    	    mappedBy = "account")
    private Trader trader;

    public Account(){}
    
	public Account(String userName, String password, String role, short enabled) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public short getEnabled() {
		return enabled;
	}

	public void setEnabled(short enabled) {
		this.enabled = enabled;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}
    
    /*public Account(){}
    
    public Account(String phoneNum, String userName, String password, String role, short enabled) {
		super();
		this.phoneNum = phoneNum;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.enabled = enabled;
	}*/
    
    
    
}
