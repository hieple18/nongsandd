package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public List<Commune> getCommuneByDistrictID(int districtID){
        List<Commune> communes = communeReponsitory.getCommuneByDistrictID(districtID);
        return communes;
    }
    
    public List<Hamlet> getHamletByCommnune(int communeID) {
        List<Hamlet> hamlets = hamletReponsitory.getHamletByCommuneID(communeID);
        return hamlets;
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
    	Hamlet hamlet = new Hamlet();
    	hamlet.setHamletID(hamletID);
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
}
