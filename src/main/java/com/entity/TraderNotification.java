package com.entity;

import java.io.Serializable;

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
@Table(name="Trader_Notification")
public class TraderNotification implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    
    @Column(name = "content", length = Constant.COLUMN_LENGTH_100_LIMIT)
    private String content;
    
    @Column(name = "link", length = Constant.COLUMN_LENGTH_100_LIMIT)
    private String link;

    @Column(name = "dateCreate")
    private long dateCreate;
    
    @Column(name = "timeAgo", length = Constant.COLUMN_LENGTH_10_LIMIT)
    private String timeAgo;
    
    @Column(name = "status")
    private int status;

    /////////////////////////

    public TraderNotification(){}
    
	public TraderNotification(Trader trader, User user, String content, String link, long dateCreate, int status) {
		super();
		this.trader = trader;
		this.user = user;
		this.content = content;
		this.link = link;
		this.dateCreate = dateCreate;
		this.status = status;
		
		this.timeAgo = "";
	}
	
	public long getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(long dateCreate) {
		this.dateCreate = dateCreate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public int getId() {
		return id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
