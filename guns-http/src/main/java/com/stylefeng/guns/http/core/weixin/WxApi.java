package com.stylefeng.guns.http.core.weixin;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.RedisUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.http.core.weixin.env.WxProperties;
import com.stylefeng.guns.http.core.weixin.templateMsg.WechatTemplate;
import com.stylefeng.guns.http.core.weixin.tool.ResultCheck;
import com.stylefeng.guns.http.core.weixin.wxobj.AccessToken;
import com.stylefeng.guns.http.core.weixin.wxobj.OAuth2AccessToken;
import com.stylefeng.guns.http.core.weixin.wxobj.OpenUser;

/**
* create by guanqing
* 2019年11月25日 下午5:49:37
*/
@Component
public class WxApi {
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WxProperties wxProperties;
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 获取accessToken
	 * @return
	 */
	public AccessToken accessToken () {
		Object accessTokenObj = redisUtil.get(wxProperties.getAppId());
		if (ToolUtil.isNotEmpty(accessTokenObj)) {
			return JSON.parseObject(String.valueOf(accessTokenObj), AccessToken.class);
		}
		return refreshAccessToken();
	}

	/**
	 * 刷新accessToken
	 * @return
	 */
	synchronized private AccessToken refreshAccessToken() {
		// TODO Auto-generated method stub
		Object accessTokenObj = redisUtil.get(wxProperties.getAppId());
		if (ToolUtil.isEmpty(accessTokenObj)) {
			AccessToken accessToken = restTemplate.getForObject(wxProperties.getApiUrl().getAccessTokenUrl(), AccessToken.class);
			if (ResultCheck.isSuccess(accessToken)) {
				accessToken.setLastRefreshTime(System.currentTimeMillis());
				redisUtil.set(wxProperties.getAppId(), JSON.toJSONString(accessToken), AccessToken.EXPIRE_TIME);
			} else {
				throw new GunsException(500, "accessToken获取失败>>accessToken>>"+accessToken);
			}
			return accessToken;
		} else {
			return JSON.parseObject(String.valueOf(accessTokenObj), AccessToken.class);
		}
	}

	/**
	 * 微信授权
	 * @param code
	 * @return
	 */
	public OAuth2AccessToken getOAuth2AccessToken(String code) {
		// TODO Auto-generated method stub
		String result = restTemplate.getForObject(MessageFormat.format(wxProperties.getApiUrl().getAuthAccessTokenUrl(), code), String.class);
		return JSON.parseObject(result, OAuth2AccessToken.class);
	}

	/**
	 * 获取用户信息
	 * @param openId
	 * @return
	 */
	public OpenUser getUserInfo(String openId) {
		// TODO Auto-generated method stub
		String userUrl = MessageFormat.format(wxProperties.getApiUrl().getUserInfoUrl(), accessToken().getAccessToken(), openId);
		return restTemplate.getForObject(userUrl, OpenUser.class);
	}
	
	/**
	 * 发送模板消息
	 * @return
	 */
	public <T> T sendTemplateMessage(WechatTemplate template, Class<T> c) {
		String templateMsgUrl = MessageFormat.format(wxProperties.getApiUrl().getSendTemplateMsgUrl(), accessToken().getAccessToken());
		return restTemplate.postForObject(templateMsgUrl, template, c);
	}
}
