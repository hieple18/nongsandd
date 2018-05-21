package com.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
*@author HiepLe
*@version 1.0 Dec 23, 2017
*/

@Entity
@Table(name="Agri_Price")
public class AgriPrice implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;  

    public AgriPrice(float price, float priceChange, Date date, Agriculture agriculture) {
		super();
		this.price = price;
		this.priceChange = priceChange;
		this.date = date;
		this.agriculture = agriculture;
	}

    public AgriPrice() {
	}
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "price")
    private float price;
    
    @Column(name = "pricechange")
    private float priceChange;
    
    @Column(name = "date")
    private Date date;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "agriID", referencedColumnName = "id")
    private Agriculture agriculture;

    public float getPriceChange() {
		return priceChange;
	}

	public void setPriceChange(float priceChange) {
		this.priceChange = priceChange;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Agriculture getAgriculture() {
        return agriculture;
    }

    public void setAgriculture(Agriculture agriculture) {
        this.agriculture = agriculture;
    }
    
    

}
