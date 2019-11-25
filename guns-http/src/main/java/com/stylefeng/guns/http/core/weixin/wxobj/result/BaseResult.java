package com.stylefeng.guns.http.core.weixin.wxobj.result;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* create by guanqing
* 2019年11月25日 下午5:45:20
*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult {

	@JsonProperty("errcode")
	@JSONField(name = "errcode")
	private String errCode;
	
	@JsonProperty("errmsg")
	@JSONField(name = "errmsg")
	private String errMsg;
	
}
