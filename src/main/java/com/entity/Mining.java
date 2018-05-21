package com.entity;

import java.io.Serializable;
import java.util.HashMap;

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
 * @author: HiepLe
 * @version: May 15, 2018
 */

@Entity
@Table(name="Mining")
public class Mining implements Serializable{
	
	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    @Column(name="agriID")
    private int agriID;
    
    @Column(name="area")
    private String area;
    
    @Column(name="quanlity")
    private String quanlity;
    
    @Column(name="commmuneID")
    private String communeID;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;
    
    public Mining(){
    	area = "";
    	quanlity = "";
    	communeID = "";
    };

	public int getAgriID() {
		return agriID;
	}
	
	public void setAgriID(int agriID) {
		this.agriID = agriID;
	}
	
	public Trader getTrader() {
		return trader;
	}

	public void setTrader(Trader trader) {
		this.trader = trader;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public HashMap<Integer, Integer> getArea() {
		if(area.equals("")){
			return new HashMap<>(); 
		}else{
			return convertToHashMap(area);
		}
	}

	public HashMap<Integer, Integer> getQuanlity() {
		if(area.equals("")){
			return new HashMap<>(); 
		}else{
			return convertToHashMap(quanlity);
		}
		
	}

	public void setQuanlity(String quanlity) {
		this.quanlity = quanlity;
	}

	public HashMap<Integer, Integer> getCommuneID() {
		if(area.equals("")){
			return new HashMap<>(); 
		}else{
			return convertToHashMap(communeID);
		}
	}

	public void setCommuneID(String communeID) {
		this.communeID = communeID;
	}
    
    public HashMap<Integer, Integer> convertToHashMap(String s){
    	HashMap<Integer, Integer> maps = new HashMap<>();
		
		String[] parts1 = s.split(";");
		for (String string : parts1) {
			String[] parts2 = string.split(",");
			int key = Integer.parseInt(parts2[0]);
			int value = Integer.parseInt(parts2[1]);
			maps.put(key, value);
		}
		
		return maps;
    }
    
    public void update(HashMap<Integer, Integer> areas, HashMap<Integer, Integer> quanlitys, 
    		HashMap<Integer, Integer> communeIDs, int agriID, Trader trader){
    	// init string
    	area = "";
    	quanlity = "";
    	communeID = "";
    	
    	// convent hashmap to string
    	areas.forEach((k,v) -> {
    		area = area + k.toString() + "," + v.toString() + ";"; 
    	});
    	quanlitys.forEach((k,v) -> {
    		quanlity = quanlity + k.toString() + "," + v.toString() + ";"; 
    	});
    	communeIDs.forEach((k,v) -> {
    		communeID = communeID + k.toString() + "," + v.toString() + ";"; 
    	});
    	
    	// remove ; character in the end
    	area = area.substring(0, area.length() - 1);
    	quanlity = quanlity.substring(0, quanlity.length() - 1);
    	communeID = communeID.substring(0, communeID.length() - 1);
    	
    	this.agriID = agriID;
    	this.trader = trader;
    }
}
