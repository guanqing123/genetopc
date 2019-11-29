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
		template.setUrl("http://wxdev.hongyancloud.com/wx#/PersonCenter/Wait");
		
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
		log.error("新预约提醒: openId>"+openId+"||xmmc="+xmmc+"||response="+response);
	}
	
	/**
	 * 报名成功提醒
	 */
	public TemplateResponse sendEnrollPass(String openId, String xmmc, String jzsj, String name) {
		WechatTemplate template = new WechatTemplate();
		template.setTouser(openId);
		template.setTemplate_id(WxConstant.TEMPLATE_ENROLL_PASS);
		template.setUrl("http://wxdev.hongyancloud.com/wx#/PersonCenter/Pass");
		
		Map<String, TemplateData> data = new HashMap<>();
		TemplateData first = new TemplateData();
		first.setValue("您报名的  " + xmmc + " 成功");
		first.setColor("#173177");
		
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(xmmc);
		keyword1.setColor("#173177");
		
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(jzsj);
		keyword2.setColor("#173177");
		
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(name);
		keyword3.setColor("#173177");
		
		TemplateData remark = new TemplateData();
		remark.setValue("请查看详情");
		remark.setColor("#173177");
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		data.put("keyword3", keyword3);
		data.put("remark", remark);
		template.setData(data);
		TemplateResponse response = wxApi.sendTemplateMessage(template, TemplateResponse.class);
		log.error("报名成功提醒: openId>"+openId+"||xmmc="+xmmc+"||response="+response);
		return response;
	}
	
	/**
	 * 报名失败提醒
	 */
	public TemplateResponse sendEnrollRefuse(String openId, String xmmc, String jzsj, String name) {
		WechatTemplate template = new WechatTemplate();
		template.setTouser(openId);
		template.setTemplate_id(WxConstant.TEMPLATE_ENROLL_REFUSE);
		template.setUrl("http://wxdev.hongyancloud.com/wx#/PersonCenter/Refuse");
		
		Map<String, TemplateData> data = new HashMap<>();
		TemplateData first = new TemplateData();
		first.setValue("您报名的失败");
		first.setColor("#FF0000");
		
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(xmmc);
		keyword1.setColor("#FF0000");
		
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(jzsj);
		keyword2.setColor("#FF0000");
		
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(name);
		keyword3.setColor("#FF0000");
		
		TemplateData remark = new TemplateData();
		remark.setValue("请查看详情,按要求补提交材料");
		remark.setColor("#FF0000");
		
		data.put("first", first);
		data.put("keyword1", keyword1);
		data.put("keyword2", keyword2);
		data.put("keyword3", keyword3);
		data.put("remark", remark);
		template.setData(data);
		TemplateResponse response = wxApi.sendTemplateMessage(template, TemplateResponse.class);
		log.error("报名失败提醒: openId>"+openId+"||xmmc="+xmmc+"||response="+response);
		return response;
	}
}
