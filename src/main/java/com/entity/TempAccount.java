package com.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.constant.Constant;

/**
 * @author: HiepLe
 * @version: Jun 10, 2018
 */

@Entity
@Table(name="Temp_account")
public class TempAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
    
    @Column(name="username", length = Constant.COLUMN_LENGTH_20_LIMIT)
    private String userName;
    
    @Column(name="password", length = Constant.COLUMN_LENGTH_100_LIMIT)
    private String password;
    
    @Column(name="code")    
    private int code;

    public TempAccount(){}
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public TempAccount(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}
}