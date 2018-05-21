package com.models;

/**
 * @author: HiepLe
 * @version: May 18, 2018
 */
public class PieChart {
	String name;
	float y;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public PieChart(String name, float y) {
		super();
		this.name = name;
		this.y = y;
	}
	public PieChart() {
		super();
	}

}
