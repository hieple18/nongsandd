package com.models;
/**
*@author HiepLe
*@version 1.0 Nov 15, 2017
*/


public class Response {
    int saleID;
    int agriID;
    int area;
    int quanlity;
    int price;
    int communeID;
    
    public Response(){};
    
	public Response(int saleID, int agriID, int area, int quanlity, int price, int communeID) {
		super();
		this.saleID = saleID;
		this.agriID = agriID;
		this.area = area;
		this.quanlity = quanlity;
		this.price = price;
		this.communeID = communeID;
	}
	
	
	public int getAgriID() {
		return agriID;
	}

	public void setAgriID(int agriID) {
		this.agriID = agriID;
	}

	public int getSaleID() {
		return saleID;
	}
	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public int getQuanlity() {
		return quanlity;
	}
	public void setQuanlity(int quanlity) {
		this.quanlity = quanlity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getCommuneID() {
		return communeID;
	}
	public void setCommuneID(int communeID) {
		this.communeID = communeID;
	}
    
    
}
