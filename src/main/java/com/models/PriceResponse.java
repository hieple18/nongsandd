package com.models;

import com.entity.AgriPrice;
import com.entity.Agriculture;

public class PriceResponse {
	private AgriPrice agriPrice;
	private Agriculture agriculture;
	private float max;
	private float min;
	private float avg;
	
	public AgriPrice getAgriPrice() {
		return agriPrice;
	}

	public void setAgriPrice(AgriPrice agriPrice) {
		this.agriPrice = agriPrice;
	}

	public Agriculture getAgriculture() {
		return agriculture;
	}

	public void setAgriculture(Agriculture agriculture) {
		this.agriculture = agriculture;
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

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}

	public PriceResponse(AgriPrice agriPrice, Agriculture agriculture, float max, float min, float avg) {
		super();
		this.agriPrice = agriPrice;
		this.agriculture = agriculture;
		this.max = max;
		this.min = min;
		this.avg = avg;
	}
	
	
}
