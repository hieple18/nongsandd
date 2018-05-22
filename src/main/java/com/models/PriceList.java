package com.models;

import java.util.List;

/**
 * @author: HiepLe
 * @version: May 21, 2018
 */
public class PriceList {
	List<PriceR> prices;

	public List<PriceR> getPrices() {
		return prices;
	}

	public void setPrices(List<PriceR> prices) {
		this.prices = prices;
	}

	public PriceList(List<PriceR> lists) {
		super();
		this.prices = lists;
	}
	
	public PriceList(){
		
	};
}
