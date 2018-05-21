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
@Table(name="user_Notification")
public class UserNotification implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;
    
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

    public UserNotification(){}
    
	public UserNotification(User user, Trader trader, String content, long dateCreate, String link, int status) {
		super();
		this.user = user;
		this.trader = trader;
		this.content = content;
		this.link = link;
		this.dateCreate = dateCreate;
		this.status = status;
		
		this.timeAgo = "";
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public long getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(long dateCreate) {
		this.dateCreate = dateCreate;
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
	
	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public void setId(int id) {
		this.id = id;
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