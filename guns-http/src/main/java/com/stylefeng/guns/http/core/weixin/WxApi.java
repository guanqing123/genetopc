package com.stylefeng.guns.http.core.weixin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.stylefeng.guns.http.core.weixin.wxobj.OAuth2AccessToken;

/**
* create by guanqing
* 2019年11月25日 下午5:49:37
*/
@Component
public class WxApi {
	
	@Autowired
	private RestTemplate restTemplate;

	public OAuth2AccessToken getOAuth2AccessToken(String code) {
		// TODO Auto-generated method stub
		return null;
	}
}
