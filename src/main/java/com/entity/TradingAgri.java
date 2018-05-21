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
@Table(name="Trading_Agri")
public class TradingAgri implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "agriID", referencedColumnName = "id")
    private Agriculture agriculture;

    
    ////////////////////////////////
    
    public TradingAgri(){}
    
    public TradingAgri(Trader trader, Agriculture agriculture) {
		super();
		this.trader = trader;
		this.agriculture = agriculture;
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

	public Agriculture getAgriculture() {
		return agriculture;
	}

	public void setAgriculture(Agriculture agriculture) {
		this.agriculture = agriculture;
	}
    
    
    
}

