package com.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.constant.Constant;

@Entity
@Table(name="trader_Cmt")
public class TraderCmt implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    
    @Column(name = "content", length = Constant.COLUMN_LENGTH_300_LIMIT)
    private String content;
    
    @Column(name = "ratingCount")
    private int ratingCount;
    
    @Column(name = "dateCreate")
    private Date dateCreate;
    
    @Column(name = "status")
    private int status;

    ////////////////////////////
    
	public int getId() {
		return id;
	}

	public TraderCmt(Trader trader, User user, String content, int ratingCount, Date dateCreate, int status) {
		super();
		this.trader = trader;
		this.user = user;
		this.content = content;
		this.ratingCount = ratingCount;
		this.dateCreate = dateCreate;
		this.status = status;
	}

	public TraderCmt() {
		super();
	}

	public int getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(int ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
    
}
