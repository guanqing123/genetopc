package com.stylefeng.guns.http.core.weixin.templateMsg;
/**
* create by guanqing
* 2019年11月27日 上午9:05:05
*/

import java.util.Map;

import lombok.Data;

@Data
public class WechatTemplate {
	
	/**
	 * 接收者openid
	 */
	private String touser;
	
	/**
	 * 模板ID
	 */
	private String template_id;
	
	/**
	 * 模板跳转链接
	 */
	private String url;
	
	/**
	 * 模板数据
	 */
	private Map<String, TemplateData> data;
	
	@Data
    public static class TemplateData {
		/**
		 * 值
		 */
        private String value;
        /**
         * 颜色
         */
        private String color;
    }
}
