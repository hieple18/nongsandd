package com.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.constant.Constant;
import com.entity.Address;
import com.entity.Commune;
import com.entity.Hamlet;
import com.reponsitory.AddressRepository;
import com.reponsitory.CommuneReponsitory;
import com.reponsitory.HamletRepository;

/**
*@author HiepLe
*@version 1.0 Dec 19, 2017
*/
@Service
public class AddressService {

    @Autowired
    private CommuneReponsitory communeReponsitory;
    
    @Autowired 
    private HamletRepository hamletReponsitory;
    
    @Autowired 
    private AddressRepository addressRepository;
    
    public void randomAddress(Address address, int hamletID) {
		int communeID = getCommuneIDByHamletID(hamletID);
		List<Double> map = Constant.GET_LNG(communeID);
		Random r = new Random();

		// map.get(0): max_lng, map.get(1): min_lng
		double lng = map.get(1) + r.nextDouble() * (map.get(0) - map.get(1));

		// map.get(2): max_lat, map.get(3): min_lat
		double lat = map.get(3) + r.nextDouble() * (map.get(2) - map.get(3));
		
		address.setLng(lng);
		address.setLat(lat);
	}
    
    public void randomLngLat(Address address, int hamletID){
    	
    }
    
    public List<Commune> getCommuneByDistrictID(int districtID){
        List<Commune> communes = communeReponsitory.getCommuneByDistrictID(districtID);
        return communes;
    }
    
    public List<Hamlet> getHamletByCommnune(int communeID) {
        List<Hamlet> hamlets = hamletReponsitory.getHamletByCommuneID(communeID);
        return hamlets;
    }
    
    public int getHamletByAddressID(int address) {
        int hamletID = addressRepository.getHamletIDByAddress(address);
        return hamletID;
    }
    
    public List<Object[]> getHamletNameByCommuneID(int communeID) {
        List<Object[]> hamlets = hamletReponsitory.getHamletNameByCommuneID(communeID);
        return hamlets;
    }
    
    public String getAddressByHamletID(int hamletID, int communeID) {
        String hamletName = hamletReponsitory.getHamletName(hamletID);
        String communeName = communeReponsitory.getCommuneName(communeID);
        
        return hamletName + ", " + communeName;
    }
    
    public int getCommuneIDByHamletID(int hamletID) {
        int communeID = hamletReponsitory.getCommuneIDByHamletID(hamletID);
        return communeID;
    }
    
    public void createAddress(Address address, int hamletID){
    	Hamlet hamlet = hamletReponsitory.findOne(hamletID);
    	
    	address.setAddress(hamlet.getAddress());
    	if(address.getLat() == 0) // if user do not select a point on maps then random lat and lng in commune scope
    		randomAddress(address, hamletID);
    	
    	address.setHamlet(hamlet);
    	addressRepository.save(address);
    }
    
    /*public void updateAddress(){
    	List<Commune> communes = communeReponsitory.findAll();
    	
    	for (Commune commune : communes) {
    		String communeName = commune.getName();
			List<Hamlet> hamlets = hamletReponsitory.getHamletByCommuneID(commune.getCommuneID());
			
			for (Hamlet hamlet : hamlets) {
				String hamletName = hamlet.getName();
				String name = hamletName + ", " + communeName;
				hamletReponsitory.updateAddress(name, hamlet.getHamletID());
			}
		}
    }*/
    
    public Address getAddressByID(int id){
    	return addressRepository.findOne(id);
    }
    
    public void updateAddress(){
    	addressRepository.updateAddress();
    }
    
    public String getCommuneName(int communeID){
    	return communeReponsitory.getCommuneName(communeID);
    }
    
    public void deleteAddress(int id){
    	addressRepository.delete(id);
    }
}
