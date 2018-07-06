package com.models;

/**
 * @author: HiepLe
 * @version: Jun 7, 2018
 */
public class PriceRecord {
	int id;
	String name;
	float price;
	float change;
	float min;
	float max;
	float avg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getChange() {
		return change;
	}
	public void setChange(float change) {
		this.change = change;
	}
	public float getMin() {
		return min;
	}
	public void setMin(float min) {
		this.min = min;
	}
	public float getMax() {
		return max;
	}
	public void setMax(float max) {
		this.max = max;
	}
	public float getAvg() {
		return avg;
	}
	public void setAvg(float avg) {
		this.avg = avg;
	}
	public PriceRecord(int id, String name, float price, float change, float min, float max, float avg) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.change = change;
		this.min = min;
		this.max = max;
		this.avg = avg;
	}
}
