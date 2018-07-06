package com.models;

/**
 * @author: HiepLe
 * @version: May 20, 2018
 */
public class PriceR {
	float price;
	int agriID;
	String name;

	public PriceR() {
		super();
	}

	public PriceR(float price, int agriID, String name) {
		super();
		this.price = price;
		this.agriID = agriID;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}