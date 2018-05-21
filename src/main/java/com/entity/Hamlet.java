package com.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.constant.Constant;

/**
*@author HiepLe
*@version 1.0 Dec 9, 2017
*/

@Entity
@Table(name="hamlet")
public class Hamlet implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "hamletID")
    private int hamletID;
    
    @Column(name = "name", length = Constant.COLUMN_LENGTH_30_LIMIT)
    private String name;
    
    @Column(name = "address", length = Constant.COLUMN_LENGTH_50_LIMIT)
    private String address;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "communeID")
    private Commune commune;
    
    @OneToMany(mappedBy = "hamlet")
    private Set<Address> adressSet = new HashSet<>();

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Hamlet() {
		
	}

	public int getHamletID() {
		return hamletID;
	}

	public void setHamletID(int hamletID) {
		this.hamletID = hamletID;
	}

	public Set<Address> getAdressSet() {
		return adressSet;
	}

	public void setAdressSet(Set<Address> adressSet) {
		this.adressSet = adressSet;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

}
