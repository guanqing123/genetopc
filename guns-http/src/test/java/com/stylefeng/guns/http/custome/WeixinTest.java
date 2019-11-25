package com.stylefeng.guns.http.custome;

import java.text.MessageFormat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.stylefeng.guns.http.base.BaseJunit;
import com.stylefeng.guns.http.core.weixin.env.WxProperties;

/**
* create by guanqing
* 2019年11月25日 下午8:57:35
*/
public class WeixinTest extends BaseJunit {

	@Autowired
	private WxProperties wxProperties;
	
	@Test
	public void getOAuth2AccessToken() {
		System.out.println(MessageFormat.format(wxProperties.getApiUrl().getAuthAccessTokenUrl(), "abcdefghijklmn"));
	}
	
}
