/**
 * 
 */
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.constant.Constant;

@Entity
@Table(name="Sale")
public class Sale implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "quantity", nullable = true)
    private float quantity;
    
    @Column(name = "area")
    private float area;
    
    @Column(name = "sellingDescribe", length = Constant.COLUMN_LENGTH_300_LIMIT, nullable = true)
    private String describe;
    
    @Column(name = "dateCreate")
    private Date dateCreate;
    
    @Column(name = "price", nullable = true)
    private long price;
    
    @Column(name = "requestCount")
    private int requestCount;
    
    @Column(name = "communeID")
    private int communeID;
    
    @Column(name = "status")
    private int status;

	@OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "address", referencedColumnName = "id")
    private Address address;
    
    @OneToMany(mappedBy = "sale")
    private Set<TradingData> datas = new HashSet<>();
    
    @OneToMany(mappedBy = "sale")
    private Set<TradingHistory> histories = new HashSet<>();
    
    @OneToMany(mappedBy = "sale")
    private Set<ImgLink> imgLinks = new HashSet<>();
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "id")
    private User user;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "agriID", referencedColumnName = "id")
    private Agriculture agriculture;

    ////////////////////////////////////////
    
    public Sale(){
    	requestCount = 0;
    }

	public int getCommuneID() {
		return communeID;
	}

	public void setCommuneID(int communeID) {
		this.communeID = communeID;
	}
    
    public int getRequestCount() {
		return requestCount;
	}

	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}

	public Sale(int id){
    	this.id = id;
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getQuantity() {
		return quantity;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<TradingData> getDatas() {
		return datas;
	}

	public void setDatas(Set<TradingData> datas) {
		this.datas = datas;
	}

	public Set<TradingHistory> getHistories() {
		return histories;
	}

	public void setHistories(Set<TradingHistory> histories) {
		this.histories = histories;
	}

	public Set<ImgLink> getImgLinks() {
		return imgLinks;
	}

	public void setImgLinks(Set<ImgLink> imgLinks) {
		this.imgLinks = imgLinks;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agriculture getAgriculture() {
		return agriculture;
	}

	public void setAgriculture(Agriculture agriculture) {
		this.agriculture = agriculture;
	}
}