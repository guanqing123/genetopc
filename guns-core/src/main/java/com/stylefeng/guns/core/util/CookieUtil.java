package com.stylefeng.guns.core.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* create by guanqing
* 2019年11月25日 下午6:16:58
*/
public class CookieUtil {

	/**
	 * 删除 openId
	 * @param response
	 */
	public static void removeCookie(String cookieName,HttpServletResponse response) {
		addCookie(cookieName, null, 0, response);
	}

	/**
	 * 添加cookie
	 * @param cookieName
	 * @param data
	 * @param expires
	 * @param response
	 */
	public static void addCookie(String cookieName, String data, int expires, HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, data);
		cookie.setMaxAge(expires);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	/**
	 * 获取 cookie
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static String getCookieValue(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String value = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equalsIgnoreCase(cookieName)) {
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
	
}
