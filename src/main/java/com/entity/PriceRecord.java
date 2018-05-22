package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: HiepLe
 * @version: May 22, 2018
 */

@Entity
@Table(name="Price_Record")
public class PriceRecord {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "year")
    private int year;
	
	@Column(name = "month")
    private int month;
	
	@Column(name = "max")
    private float max;
	
	@Column(name = "min")
    private float min;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PriceRecord(int id, int year, int month, float max, float min) {
		super();
		this.id = id;
		this.year = year;
		this.month = month;
		this.max = max;
		this.min = min;
	}

	public PriceRecord() {
		super();
	}
	
}
