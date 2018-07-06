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
import javax.persistence.OneToOne;
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
    
 // using for recommend with distinguish agriculture id
    /*@Column(name="agriID")
    private int agriID;*/
    
    @Column(name="area")
    private String area;
    
    
    @Column(name="agri")
    private String agri;
    
    @Column(name="commmuneID")
    private String communeID;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "traderID", referencedColumnName = "id")
    private Trader trader;
    
    public Mining(){
    	area = "";
    	agri = "";
    	communeID = "";
    };
	
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

	public HashMap<Integer, Integer> getAgri() {
		if(area.equals("")){
			return new HashMap<>(); 
		}else{
			return convertToHashMap(agri);
		}
		
	}

	public void setAgri(String agri) {
		this.agri = agri;
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
    		HashMap<Integer, Integer> communeIDs, Trader trader){
    	// init string
    	area = "";
    	agri = "";
    	communeID = "";
    	
    	// convent hashmap to string
    	areas.forEach((k,v) -> {
    		area = area + k.toString() + "," + v.toString() + ";"; 
    	});
    	quanlitys.forEach((k,v) -> {
    		agri = agri + k.toString() + "," + v.toString() + ";"; 
    	});
    	communeIDs.forEach((k,v) -> {
    		communeID = communeID + k.toString() + "," + v.toString() + ";"; 
    	});
    	
    	// remove ; character in the end
    	area = area.substring(0, area.length() - 1);
    	agri = agri.substring(0, agri.length() - 1);
    	communeID = communeID.substring(0, communeID.length() - 1);
    	
    	this.trader = trader;
    }
    
    public boolean isEmpty(){
    	if(!"".equals(this.agri))
    		return false;
    	if(!"".equals(this.communeID))
    		return false;
    	if(!"".equals(this.area))
    		return false;
    	
    	return true;
    }
}
