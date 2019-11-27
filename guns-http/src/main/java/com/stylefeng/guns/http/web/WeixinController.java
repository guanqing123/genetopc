package com.stylefeng.guns.http.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.util.CookieUtil;
import com.stylefeng.guns.core.util.ResultUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.http.core.weixin.WxApi;
import com.stylefeng.guns.http.core.weixin.constant.WxConstant;
import com.stylefeng.guns.http.core.weixin.tool.ResultCheck;
import com.stylefeng.guns.http.core.weixin.wxobj.OAuth2AccessToken;
import com.stylefeng.guns.http.core.weixin.wxobj.OpenUser;
import com.stylefeng.guns.http.model.User;
import com.stylefeng.guns.http.service.IUserService;

@Controller
public class WeixinController {

	@Autowired
	private WxApi wxApi;
	
	@Autowired
	private IUserService userServiceImpl;
	
	@RequestMapping("/")
	@ResponseBody
	public Result<Object> index(){
		return ResultUtil.success();
	}
	
	@GetMapping("/getWxOpenId")
	public String getWxOpenId(String code, String toUrl,HttpServletResponse response) {
		System.out.println("code>" + code +"\t toUrl>"+ toUrl);
		String openId = null;
		OAuth2AccessToken auth2AccessToken = wxApi.getOAuth2AccessToken(code);
		
		if (ResultCheck.isSuccess(auth2AccessToken))
			openId = auth2AccessToken.getOpenId();
		
		if (ToolUtil.isNotEmpty(openId))
			CookieUtil.addCookie(WxConstant.OPEN_ID, openId, WxConstant.OPEN_ID_LIVE_TIME, response);
		
		OpenUser openUser = null;
		if (ToolUtil.isNotEmpty(openId))
			openUser = wxApi.getUserInfo(openId);
		
		if (ToolUtil.isNotEmpty(openUser)) {
			User user = new User();
			BeanUtils.copyProperties(openUser, user);
			userServiceImpl.insertOrUpdate(user);
			try {
				CookieUtil.addCookie(WxConstant.NICK_NAME, URLEncoder.encode(openUser.getNickName(), "UTF-8"), WxConstant.NICK_NAME_LIVE_TIME, response);
				CookieUtil.addCookie(WxConstant.HEAD_URL, URLEncoder.encode(openUser.getHeadImgUrl(), "UTF-8"), WxConstant.HEAD_URL_LIVE_TIME, response);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "redirect:" + toUrl;
	}
	
	@GetMapping("/flushWxOpenId")
	public Result<Object> flushWxOpenId(HttpServletResponse response) {
		CookieUtil.removeCookie(WxConstant.OPEN_ID, response);
		CookieUtil.removeCookie(WxConstant.NICK_NAME, response);
		CookieUtil.removeCookie(WxConstant.HEAD_URL, response);
		return ResultUtil.success();
	}
}
