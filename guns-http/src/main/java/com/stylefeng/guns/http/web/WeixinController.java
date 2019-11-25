package com.stylefeng.guns.http.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.CookiesUtil;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.http.core.weixin.WxApi;
import com.stylefeng.guns.http.core.weixin.constant.WxConstant;
import com.stylefeng.guns.http.core.weixin.wxobj.OAuth2AccessToken;

@Controller
public class WeixinController {

	@Autowired
	private WxApi wxApi;
	
	@RequestMapping("/")
	@ResponseBody
	public Result<Object> index(){
		return ResultUtil.success();
	}
	
	@GetMapping("/getWxOpenId")
	public String getWxOpenId(String code, String toUrl,HttpServletResponse response) {
		System.out.println("code>" + code +"\t toUrl>"+ toUrl);
//		OAuth2AccessToken auth2AccessToken = wxApi.getOAuth2AccessToken(code);
		
		CookiesUtil.addCookie(WxConstant.OPEN_ID, code, WxConstant.OPEN_ID_LIVE_TIME, response);
		
		return "redirect:" + toUrl;
	}
}
