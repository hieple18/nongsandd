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

@Entity
@Table(name="User_Reaction")
public class UserReaction implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userCmtID", referencedColumnName = "id")
    private UserCmt userCmt;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    
    @Column(name = "status")
    private int status;

    ///////////////////////////////
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserCmt getUserCmt() {
		return userCmt;
	}

	public void setUserCmt(UserCmt userCmt) {
		this.userCmt = userCmt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
    
}