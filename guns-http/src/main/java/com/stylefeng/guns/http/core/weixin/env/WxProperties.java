package com.stylefeng.guns.http.core.weixin.env;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
* create by guanqing
* 2019年11月25日 下午5:58:25
*/
@Data
@Component
@ConfigurationProperties(prefix = "wx")
public class WxProperties {

	//微信接口地址对象
	private ApiUrl apiUrl;
	
	@Data
	public static class ApiUrl {
		
    	//获取用户信息所需accessToken
        private String authAccessTokenUrl;
	}
	
}
