package com.secondShop.productDeliveryAddrData.model;

public class CityVO {
	private String cityId;
	private String cityName;
	private String cityEngName;
	

	public CityVO(String cityId, String cityName, String cityEngName) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.cityEngName = cityEngName;
	}

	public CityVO() {
		super();
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityEngName() {
		return cityEngName;
	}

	public void setCityEngName(String cityEngName) {
		this.cityEngName = cityEngName;
	}
	
	
	@Override
	public String toString() {
		return "CityVO [cityId=" + cityId + ", cityName=" + cityName + ", cityEngName=" + cityEngName + ", getCityId()="
				+ getCityId() + ", getCityName()=" + getCityName() + ", getCityEngName()=" + getCityEngName()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
