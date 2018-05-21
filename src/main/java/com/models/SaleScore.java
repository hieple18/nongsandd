package com.models;

import com.entity.Sale;

/**
 * @author: HiepLe
 * @version: May 16, 2018
 */
public class SaleScore implements Comparable<SaleScore>{
	private Sale sale;
	private int score;
	
	public SaleScore(Sale sale, int score) {
		super();
		this.sale = sale;
		this.score = score;
	}

	public Sale getSale() {
		return sale;
	}
	
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(SaleScore o) {
		// TODO Auto-generated method stub
		return this.score - o.score;
	}
}
