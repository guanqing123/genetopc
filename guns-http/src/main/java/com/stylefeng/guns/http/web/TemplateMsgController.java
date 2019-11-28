package com.stylefeng.guns.http.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.http.core.weixin.templateMsg.TemplateUtil;

/**
* create by guanqing
* 2019年11月28日 下午4:40:15
*/
@Controller
@RequestMapping("/template")
public class TemplateMsgController {

	@Autowired
	private TemplateUtil templateUtil;
	
	@RequestMapping("/sendPass")
	@ResponseBody
	public Object sendEnrollPass(String openId, String xmmc, String jzsj, String name) {
		return templateUtil.sendEnrollPass(openId, xmmc, jzsj, name);
	}
	
	@RequestMapping("/sendRefuse")
	@ResponseBody
	public Object sendEnrollRefuse(String openId, String xmmc, String jzsj, String name) {
		return templateUtil.sendEnrollRefuse(openId, xmmc, jzsj, name);
	}
}
