package com.stylefeng.guns.http.core.weixin.wxobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stylefeng.guns.http.core.weixin.wxobj.result.BaseResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* create by guanqing
* 2019年11月25日 下午9:11:28
*/
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenUser extends BaseResult{

	private int subscribe;
	
	@JsonProperty("openid")
	@JSONField(name = "openid")
	private String openId;
	
	@JsonProperty("nickname")
	@JSONField(name = "nickname")
	private String nickName;
	
	private String sex;
	
	private String city;
	
	private String country;
	
	private String province;
	
	private String language;
	
	@JsonProperty("headimgurl")
	@JSONField(name = "headimgurl")
	private String headImgUrl;
	
	@JsonProperty("subscribe_time")
	@JSONField(name = "subscribe_time")
	private long subscribeTime;
	
	@JsonProperty("unionid")
	@JSONField(name = "unionid")
	private String unionId;
	
	private String remark;
	
	@JsonProperty("groupid")
	@JSONField(name = "groupid")
	private String groupId;
	
}
