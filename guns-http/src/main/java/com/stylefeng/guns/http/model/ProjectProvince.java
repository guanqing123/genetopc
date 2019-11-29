package com.stylefeng.guns.http.model;

import java.util.List;

import lombok.Data;

/**
* create by guanqing
* 2019年11月29日 下午1:41:31
*/
@Data
public class ProjectProvince {

	// 省
	private String provinceName;
	
	// 城市数组
	private List<ProjectCity> cities;
}
