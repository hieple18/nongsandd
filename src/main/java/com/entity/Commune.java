package com.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;

import com.constant.Constant;

/**
*@author HiepLe
*@version 1.0 Dec 9, 2017
*/
@Entity
@Table(name="commune")
public class Commune implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "communeID")
    private int communeID;
    
    @Column(name = "name", length = Constant.COLUMN_LENGTH_30_LIMIT)
    private String name;
    
    @OneToMany(mappedBy = "commune")
    private Set<Hamlet> hamletSet = new HashSet<>();
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "districtID")
    private District district;

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public int getCommuneID() {
        return communeID;
    }

    public void setCommuneID(int communeID) {
        this.communeID = communeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Hamlet> getHamletSet() {
        return hamletSet;
    }

    public void setHamletSet(Set<Hamlet> hamletSet) {
        this.hamletSet = hamletSet;
    }
    
}
