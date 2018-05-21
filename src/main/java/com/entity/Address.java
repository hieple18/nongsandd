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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.constant.Constant;


@Entity
@Table(name = "Address")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name="lat") 
    private double lat;
    
    @Column(name="lng") 
    private double lng;
    
    @Column(name = "address", length = Constant.COLUMN_LENGTH_50_LIMIT)
    private String address;
    
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "address")
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "address")
    private Trader trader;
    
    @OneToOne(fetch=FetchType.LAZY,
            mappedBy = "address")
    private Sale sale;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "hamletID")
    private Hamlet hamlet;
    
    public Address(){}
    
	public Address(double lat, double lng, Hamlet hamlet) {
		super();
		this.lat = lat;
		this.lng = lng;
		this.hamlet = hamlet;
	}

	public Address(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
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

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Hamlet getHamlet() {
		return hamlet;
	}

	public void setHamlet(Hamlet hamlet) {
		this.hamlet = hamlet;
	}

}
