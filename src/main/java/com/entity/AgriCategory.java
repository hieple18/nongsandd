package com.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.constant.Constant;

/**
*@author HiepLe
*@version 1.0 Dec 17, 2017
*/

@Entity
@Table(name="Agri_Category")
public class AgriCategory implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "name", length = Constant.COLUMN_LENGTH_20_LIMIT)
    private String name;
    
    @OneToMany(mappedBy = "agriCategory")
    private Set<Agriculture> agricultures = new HashSet<>();
    
    public AgriCategory(int id) {
		super();
		this.id = id;
	}

	public AgriCategory() {
		super();
	}

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

}
