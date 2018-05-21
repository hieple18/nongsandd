package com.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.constant.Constant;

/**
*@author HiepLe
*@version 1.0 Dec 9, 2017
*/

@Entity
@Table(name="district")
public class District implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "districtID")
    private int districtID;
    
    @Column(name = "name", length = Constant.COLUMN_LENGTH_30_LIMIT)
    private String name;
    
    @Column(name = "provinceID")
    private String provinceID;
    
    @OneToMany(mappedBy = "district")
    private Set<Commune> communeSet = new HashSet<>();
    
    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(String provinceID) {
        this.provinceID = provinceID;
    }

    public Set<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }

    @OneToMany(mappedBy = "district")
    private Set<Commune> communes;

}
