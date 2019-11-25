package com.stylefeng.guns.http.core.weixin.wxobj;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stylefeng.guns.http.core.weixin.wxobj.result.BaseResult;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* create by guanqing
* 2019年11月25日 下午5:48:31
*/
@Getter
@Setter
@ToString
public class OAuth2AccessToken extends BaseResult {

	//获取到的凭证
	@JsonProperty("access_token")
	@JSONField(name = "access_token")
	private String accessToken;
	
    // 凭证有效时间，单位：秒
    @JsonProperty("expires_in")
    @JSONField(name = "expires_in")
	private int expiresIn;
    
    @JsonProperty("refresh_token")
    @JSONField(name = "refresh_token")
    private String refreshToken;
    
    @JsonProperty("openid")
    @JSONField(name = "openid")
    private String openId;
    
    private String scope;
	
}
