package com.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: HiepLe
 * @version: May 21, 2018
 */
public class PriceList {
	List<PriceR> prices;

	public List<PriceR> getLists() {
		return prices;
	}

	public void setLists(List<PriceR> lists) {
		this.prices = lists;
	}

	public PriceList(List<PriceR> lists) {
		super();
		this.prices = lists;
	}
	
	public PriceList(){
		
	};
	
	public void initList(){
		prices = new ArrayList<PriceR>();
	}
}
