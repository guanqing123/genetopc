package com.stylefeng.guns.modular.custom.model;

import java.util.List;

import lombok.Data;

/**
* create by guanqing
* 2019年11月29日 上午9:41:52
*/
@Data
public class CityHospital {

	private String provinceName;
	
	private List<City> children;
	
	@Data
	public static class City {
		
		private Integer cityid;
		
		private String cityName;
		
	}
	
}
