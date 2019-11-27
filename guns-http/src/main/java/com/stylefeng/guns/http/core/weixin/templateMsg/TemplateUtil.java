package com.stylefeng.guns.http.core.weixin.templateMsg;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.http.core.weixin.WxApi;
import com.stylefeng.guns.http.core.weixin.constant.WxConstant;
import com.stylefeng.guns.http.core.weixin.templateMsg.WechatTemplate.TemplateData;

import lombok.extern.slf4j.Slf4j;

/**
* create by guanqing
* 2019年11月27日 下午1:18:04
*/
@Component
@Slf4j
public class TemplateUtil {

	@Autowired
	private WxApi wxApi;
	/**
	 * 新预约提醒
	 */
	public void sendNewAppointmentReminder (String openId, String xmmc, String commitDate) {
		WechatTemplate template = new WechatTemplate();
		template.setTouser(openId);
		template.setTemplate_id(WxConstant.TEMPLATE_NEW_APPOINTMENT_REMINDER);
		template.setUrl("https://www.baidu.com");
		
		Map<String, TemplateData> data = new HashMap<>();
		TemplateData first = new TemplateData();
		first.setValue("您有一个新的预约待处理");
		first.setColor("#173177");
		
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(commitDate);
		keyword1.setColor("#173177");
		
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(xmmc);
		keyword2.setColor("#173177");
		
		TemplateData remark = new TemplateData();
		remark.setValue("请及时处理");
		remark.setColor("#173177");
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		data.put("remark", remark);
		template.setData(data);
		TemplateResponse response = wxApi.sendTemplateMessage(template, TemplateResponse.class);
		log.error("openId>"+openId+"||xmmc="+xmmc+"||response="+response);
	}
}
