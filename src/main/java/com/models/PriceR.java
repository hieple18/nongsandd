package com.models;

/**
 * @author: HiepLe
 * @version: May 20, 2018
 */
public class PriceR {
	float price;
	int agriID;

	public PriceR() {
		super();
	}

	public PriceR(float price, int agriID) {
		super();
		this.price = price;
		this.agriID = agriID;
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public int getAgriID() {
		return agriID;
	}

	public void setAgriID(int agriID) {
		this.agriID = agriID;
	}
	
	
}