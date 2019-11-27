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

	//开发者ID
	private String appId;
	//开发者密码
	private String appSecret;
	//微信接口地址对象
	private ApiUrl apiUrl;
	
	@Data
	public static class ApiUrl {
    	
		//获取accessTokenUrl
		private String accessTokenUrl;
    	//获取用户信息所需accessToken
        private String authAccessTokenUrl;
        //获取用户信息
        private String userInfoUrl;
        //发送模板消息
        private String sendTemplateMsgUrl;
	}
}
