package com.models;

import java.util.List;

import com.entity.AgriPrice;

/**
 * @author: HiepLe
 * @version: May 20, 2018
 */
public class PriceU {
	List<AgriPrice> prices;

	public List<AgriPrice> getPrices() {
		return prices;
	}

	public void setPrices(List<AgriPrice> prices) {
		this.prices = prices;
	}

	public PriceU(List<AgriPrice> prices) {
		super();
		this.prices = prices;
	}
	
	public PriceU(){}
}